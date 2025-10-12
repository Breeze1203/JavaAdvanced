package org.pt.ratelimiter.core.impl;

import org.pt.ratelimiter.core.RateLimiterAlgorithm;
import org.pt.ratelimiter.core.RateLimiterContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SlidingWindowRateLimiter
 * @Author pt
 * @Description
 * @Date 2025/10/11 21:19
 **/
@Component(value = SlidingWindowRateLimiter.ALGORITHM_NAME)
public class SlidingWindowRateLimiter implements RateLimiterAlgorithm {
    public static final String ALGORITHM_NAME = "slidingWindow";

    private final RedisTemplate<String, String> redisTemplate;

    private static final DefaultRedisScript<Long> windowRedisScript;

    static {
        windowRedisScript = new DefaultRedisScript<>();
        windowRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/sliding_window.lua")));
        windowRedisScript.setResultType(Long.class);
    }

    public SlidingWindowRateLimiter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryAcquire(RateLimiterContext context) {
        long limit = context.getLimit();
        long period = context.getPeriod();
        TimeUnit timeUnit = context.getTimeUnit();
        // 计算窗口大小 (毫秒)
        long windowSizeInMillis = timeUnit.toMillis(period);
        // 准备Lua脚本的参数
        String key = "rate_limiter:sw:" + context.getKey();
        long currentTimeMillis = System.currentTimeMillis();
        // 创建一个唯一的 member，防止 ZSET 中成员冲突
        String member = currentTimeMillis + "-" + UUID.randomUUID().toString();
        String limitArg = String.valueOf(limit);
        String windowSizeArg = String.valueOf(windowSizeInMillis);
        String currentTsArg = String.valueOf(currentTimeMillis);
        // 执行Lua脚本
        Long result = this.redisTemplate.execute(
                windowRedisScript,
                Collections.singletonList(key),
                limitArg,
                windowSizeArg,
                currentTsArg,
                member
        );

        // 判断结果, 1表示成功, 0表示失败
        return result != null && result == 1L;
    }

    @Override
    public String getAlgorithmName() {
        return ALGORITHM_NAME;
    }
}
