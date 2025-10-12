package org.pt.ratelimiter.core.impl;

import org.pt.ratelimiter.core.RateLimiterAlgorithm;
import org.pt.ratelimiter.core.RateLimiterContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisTokenBucketRateLimiter
 * @Author pt
 * @Description
 * @Date 2025/10/10 21:04
 **/
@Component(value = RedisTokenBucketRateLimiter.ALGORITHM_NAME)
public class RedisTokenBucketRateLimiter implements RateLimiterAlgorithm {

    private final RedisTemplate<String, String> redisTemplate;

    private final static DefaultRedisScript<Long> rateLimitScript;

    static {
        rateLimitScript = new DefaultRedisScript<>();
        rateLimitScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/ratelimit.lua")));
        rateLimitScript.setResultType(Long.class);
    }
    public static final String ALGORITHM_NAME = "redisTokenBucket";

    public RedisTokenBucketRateLimiter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryAcquire(RateLimiterContext context) {
        long limit = context.getLimit();
        long period = context.getPeriod();
        TimeUnit timeUnit = context.getTimeUnit();
        //  计算令牌生成速率 (每毫秒)
        double tokensPerMillisecond = (double) limit / timeUnit.toMillis(period);
        //  设置key的过期时间，防止冷数据。一般设置为限流周期的2倍。
        long expireInSeconds = timeUnit.toSeconds(period) * 2;
        //  准备Lua脚本的参数
        String key = "rate_limiter:" + context.getKey();
        String capacityArg = String.valueOf(limit);
        String tokensPerMsArg = String.valueOf(tokensPerMillisecond);
        String currentTsArg = String.valueOf(System.currentTimeMillis());
        String requestedTokensArg = "1"; // 每次请求消耗1个令牌
        String expireArg = String.valueOf(expireInSeconds);
        // 执行Lua脚本
        Long result = this.redisTemplate.execute(
                rateLimitScript,
                Collections.singletonList(key),
                capacityArg,
                tokensPerMsArg,
                currentTsArg,
                requestedTokensArg,
                expireArg
        );
        //  判断结果, 1表示成功, 0表示失败
        return result != null && result == 1L;
    }

    @Override
    public String getAlgorithmName() {
        return ALGORITHM_NAME;
    }
}
