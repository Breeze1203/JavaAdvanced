package org.pt;

/**
 * @ClassName SlidingWindowRateLimiterService
 * @Author pt
 * @Description
 * @Date 2025/6/16 16:58
 **/
import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import java.util.Collections;

/**
 * 基于 Redis 有序集合 (Sorted Set) 实现的滑动窗口限流服务。
 */
@Service
public class SlidingWindowRateLimiterService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final static DefaultRedisScript<Long> slidingWindowScript;

    static  {
        slidingWindowScript = new DefaultRedisScript<>();
        slidingWindowScript.setResultType(Long.class);
        slidingWindowScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/sliding_window.lua")));
    }

    /**
     * 检查某个操作在滑动窗口内是否被允许。
     *
     * @param key           要限流的资源的唯一键 (e.g., "ratelimit:user:pengtao")
     * @param windowSeconds 时间窗口的大小，单位为秒 (e.g., 10)
     * @param maxRequests   在时间窗口内允许的最大请求数 (e.g., 3)
     * @return true 如果请求被允许, false 如果请求被拒绝
     */
    public boolean isAllowed(String key, int windowSeconds, int maxRequests) {
        // 调用 Redis 执行 Lua 脚本
        Long result = stringRedisTemplate.execute(
                slidingWindowScript,
                Collections.singletonList(key),   // KEYS[1]
                String.valueOf(windowSeconds),    // ARGV[1]
                String.valueOf(maxRequests)       // ARGV[2]
        );

        // 如果脚本返回 1，则表示允许
        return result != null && result == 1L;
    }

    /**
     * 使用示例：模拟一个需要限流的 API 请求。
     * @param userId 用户ID
     */
    public void handleApiRequest(String userId) {
        String key = "ratelimit:api:sliding:" + userId;
        // 设置规则：每 10 秒最多允许 3 次请求
        if (isAllowed(key, 10, 3)) {
            System.out.println("用户 " + userId + " 的请求被允许。");
            // 在这里执行核心业务逻辑...
        } else {
            System.out.println("用户 " + userId + " 的请求被拒绝，访问过于频繁！");
            // 在这里可以抛出异常或返回错误信息
        }
    }
}
