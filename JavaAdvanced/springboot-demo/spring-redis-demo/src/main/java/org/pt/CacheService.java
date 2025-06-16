package org.pt;

/**
 * @ClassName CacheService
 * @Author pt
 * @Description
 * @Date 2025/6/16 17:36
 **/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

// 假设的商品类
class Product {
    public String id;
    public String name;
    public double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

@Service
public class CacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private DistributedLockService lockService;

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 获取商品信息（防止缓存击穿）
     * @param productId 商品ID
     * @return 商品信息
     */
    public Product getProductInfo(String productId) throws InterruptedException, JsonProcessingException {
        String cacheKey = "cache:product:" + productId;

        // 1. 先从缓存读取
        String json = stringRedisTemplate.opsForValue().get(cacheKey);
        if (json != null && !json.isEmpty()) {
            System.out.println("缓存命中，直接返回: " + productId);
            return mapper.readValue(json, Product.class);
        }

        // 2. 缓存未命中，尝试获取分布式锁
        String lockKey = "lock:product_query:" + productId;
        String lockValue = UUID.randomUUID().toString();
        // 尝试获取锁，最多等待3秒，防止查数据库的线程挂掉导致死锁
        if (lockService.tryLock(lockKey, lockValue, 3, TimeUnit.SECONDS)) {
            System.out.println("缓存未命中，获取锁成功，查询数据库: " + productId);
            try {
                // 再次检查缓存（Double-Checked Locking），因为在等待锁的过程中可能已有其他线程查完并写入了缓存
                json = stringRedisTemplate.opsForValue().get(cacheKey);
                if (json != null && !json.isEmpty()) {
                    System.out.println("获取锁后发现缓存已存在，直接返回: " + productId);
                    return mapper.readValue(json, Product.class);
                }
                // 3. 真正查询数据库
                Product productFromDB = queryDatabase(productId);
                // 4. 将结果写入缓存，并设置合理的过期时间
                if (productFromDB != null) {
                    stringRedisTemplate.opsForValue().set(
                            cacheKey,
                            mapper.writeValueAsString(productFromDB),
                            10, // 例如缓存10分钟
                            TimeUnit.MINUTES
                    );
                } else {
                    // 防止缓存穿透：如果数据库也查不到，可以缓存一个空值或短时间的占位符
                    stringRedisTemplate.opsForValue().set(cacheKey, "", 5, TimeUnit.MINUTES);
                }

                return productFromDB;
            } finally {
                // 5. 释放锁
                lockService.unlock(lockKey, lockValue);
                System.out.println("数据库查询完毕，释放锁: " + productId);
            }
        } else {
            // 6. 获取锁失败，说明有其他线程正在查询数据库
            System.out.println("获取锁失败，休眠后重试读取缓存: " + productId);
            Thread.sleep(200); // 休眠一小段时间
            return getProductInfo(productId); // 递归调用，再次尝试从缓存获取
        }
    }

    private Product queryDatabase(String productId) throws InterruptedException {
        System.out.println("--- 模拟查询数据库中... productId: " + productId + " ---");
        Thread.sleep(1000); // 模拟慢查询
        Product p = new Product();
        p.id = productId;
        p.name = "测试商品";
        p.price = 99.9;
        return p;
    }
}
