package org.pt;

/**
 * @ClassName RateLimiterService
 * @Author pt
 * @Description
 * @Date 2025/6/16 16:40
 **/

import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class RateLimiterService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final static DefaultRedisScript<Long> rateLimitScript;

    static {
        rateLimitScript = new DefaultRedisScript<>();
        rateLimitScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/ratelimit.lua")));
        rateLimitScript.setResultType(Long.class);
    }

    /**
     * 检查某个操作是否被允许
     * @param key 限流的唯一标识
     * @param windowSeconds 时间窗口（秒）
     * @param maxRequests 最大请求数
     * @return true 如果允许, false 如果被限流
     */
    public boolean isAllowed(String key, int windowSeconds, int maxRequests) {
        Long result = stringRedisTemplate.execute(
                rateLimitScript,
                Collections.singletonList(key),
                String.valueOf(windowSeconds),
                String.valueOf(maxRequests)
        );
        return result != null && result == 1L;
    }

    public void handleApiRequest(String userId) {
        String key = "ratelimit:api:user:" + userId;
        // 每60秒内，只允许用户访问10次
        if (isAllowed(key, 60, 10)) {
            System.out.println("用户 " + userId + " 访问成功。");
        } else {
            System.out.println("用户 " + userId + " 访问过于频繁，已被限流。");
        }
    }
}
