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
 * @ClassName LeakyBucketRateLimiter
 * @Author pt
 * @Description
 * @Date 2025/10/11 21:41
 **/
@Component(value = LeakyBucketRateLimiter.ALGORITHM_NAME)
public class LeakyBucketRateLimiter implements RateLimiterAlgorithm {
    public static final String ALGORITHM_NAME = "leakyBucket";

    private final RedisTemplate<String, String> redisTemplate;
    private static final DefaultRedisScript<Long> LeakyBucketScript;

    static {
        LeakyBucketScript = new DefaultRedisScript<>();
        LeakyBucketScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/leaky_bucket.lua")));
        LeakyBucketScript.setResultType(Long.class);
    }

    public LeakyBucketRateLimiter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryAcquire(RateLimiterContext context) {
        long capacity = context.getLimit(); // 注解中的 'limit' 作为桶的容量
        long period = context.getPeriod();
        TimeUnit timeUnit = context.getTimeUnit();
        // 计算漏水速率 (每秒漏出多少个)
        // period 和 limit 共同决定了速率。例如 1分钟120次 -> 120/60s = 2次/秒
        double rate = (double) capacity / timeUnit.toSeconds(period);

        // 准备Lua脚本的参数
        String key = "rate_limiter:lb:" + context.getKey(); // 加个前缀，方便管理
        // 使用浮点数表示的秒级时间戳，以支持更精确的速率计算
        String currentTimeSeconds = String.valueOf(System.currentTimeMillis() / 1000.0);

        String capacityArg = String.valueOf(capacity);
        String rateArg = String.valueOf(rate);

        //  执行Lua脚本
        Long result = this.redisTemplate.execute(
                LeakyBucketScript,
                Collections.singletonList(key),
                capacityArg,
                rateArg,
                currentTimeSeconds
        );

        //  判断结果, 1表示成功, 0表示失败
        return result != null && result == 1L;
    }

    @Override
    public String getAlgorithmName() {
        return ALGORITHM_NAME;
    }
}
