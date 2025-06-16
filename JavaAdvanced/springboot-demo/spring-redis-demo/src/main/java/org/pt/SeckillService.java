package org.pt;

/**
 * @ClassName SeckillService
 * @Author pt
 * @Description
 * @Date 2025/6/16 17:22
 **/
import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class SeckillService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final static DefaultRedisScript<Long> stockDeductScript;

    static  {
        stockDeductScript = new DefaultRedisScript<>();
        stockDeductScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/stock_deduct.lua")));
        stockDeductScript.setResultType(Long.class);
    }

    /**
     * 扣减库存
     * @param productId 商品ID
     * @return true 如果扣减成功, false otherwise
     */
    public boolean deductStock(String productId) {
        String key = "stock:product:" + productId;
        // 每次扣减 1 个库存
        Long result = stringRedisTemplate.execute(
                stockDeductScript,
                Collections.singletonList(key),
                "1"
        );
        if (result == null) {
            return false;
        }
        if (result >= 0) {
            System.out.println("商品 " + productId + " 库存扣减成功，剩余库存：" + result);
            return true;
        } else if (result == -1) {
            System.out.println("商品 " + productId + " 库存不足！");
            return false;
        } else { // result == -2
            System.out.println("商品 " + productId + " 不存在或已售罄！");
            return false;
        }
    }

    // 初始化库存以供测试
    public void setStock(String productId, int stock) {
        String key = "stock:product:" + productId;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(stock));
    }
}
