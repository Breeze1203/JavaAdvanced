
/**
 * @ClassName SeckillServiceTest
 * @Author pt
 * @Description
 * @Date 2025/6/16 17:28
**/
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pt.SeckillService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SeckillService 的并发测试类
 */
@SpringBootTest(classes = org.pt.Main.class)
public class SeckillServiceTest {

    @Resource
    private SeckillService seckillService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // --- 定义测试常量 ---
    private static final String PRODUCT_ID = "prod-1001"; // 商品ID
    private static final int INITIAL_STOCK = 10;          // 初始库存
    private static final int CONCURRENT_USERS = 100;      // 模拟并发用户数（大于库存，以测试超卖）


    /**
     * 在每个测试方法执行前，初始化 Redis 中的库存。
     */
    @BeforeEach
    public void setup() {
        seckillService.setStock(PRODUCT_ID, INITIAL_STOCK);
        System.out.println("--- 测试开始，商品ID: " + PRODUCT_ID + ", 初始库存设置为: " + INITIAL_STOCK + " ---");
    }

    @Test
    @DisplayName("多线程并发秒杀测试，验证库存不会被超卖")
    public void testConcurrentDeductStock_NoOverselling() throws InterruptedException {
        // 创建一个原子计数器，用于统计成功扣减库存的线程数
        final AtomicInteger successCount = new AtomicInteger(0);

        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_USERS);

        // 创建一个 CountDownLatch，用于等待所有线程执行完毕
        final CountDownLatch latch = new CountDownLatch(CONCURRENT_USERS);

        long startTime = System.currentTimeMillis();

        // 提交所有并发任务到线程池
        for (int i = 0; i < CONCURRENT_USERS; i++) {
            executorService.submit(() -> {
                try {
                    // 每个线程执行一次库存扣减操作
                    boolean result = seckillService.deductStock(PRODUCT_ID);
                    if (result) {
                        // 如果成功，成功计数器加1
                        successCount.incrementAndGet();
                    }
                } finally {
                    // 无论成功与否，都必须让 latch 减一
                    latch.countDown();
                }
            });
        }
        // 主线程等待所有子线程执行完毕，最多等待20秒
        latch.await(20, java.util.concurrent.TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();

        System.out.println("--- 所有线程执行完毕 ---");
        System.out.println("耗时: " + (endTime - startTime) + " ms");
        System.out.println("成功扣减库存的请求数: " + successCount.get());

        // --- 开始断言 ---
        // 1. 核心断言：验证成功秒杀的请求数是否精确等于初始库存数
        Assertions.assertEquals(INITIAL_STOCK, successCount.get(), "成功扣减的库存数应与初始库存数相等，不应超卖！");
        // 2. 辅助断言：直接查询 Redis，验证最终库存是否为 0
        String finalStock = stringRedisTemplate.opsForValue().get("stock:product:" + PRODUCT_ID);
        System.out.println("Redis 中最终库存剩余: " + finalStock);
        Assertions.assertEquals("0", finalStock, "Redis中的最终库存应为0！");
        // 关闭线程池
        executorService.shutdown();
    }
}