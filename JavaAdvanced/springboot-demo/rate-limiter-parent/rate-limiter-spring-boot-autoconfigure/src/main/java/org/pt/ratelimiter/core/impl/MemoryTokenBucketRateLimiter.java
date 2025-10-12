package org.pt.ratelimiter.core.impl;

import org.pt.ratelimiter.core.RateLimiterAlgorithm;
import org.pt.ratelimiter.core.RateLimiterContext;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
/**
 * @ClassName MemoryTokenBucketRateLimiter
 * @Author pt
 * @Description
 * @Date 2025/10/9 21:06
 **/


@Component(value = MemoryTokenBucketRateLimiter.ALGORITHM_NAME)
public class MemoryTokenBucketRateLimiter implements RateLimiterAlgorithm {

    public static final String ALGORITHM_NAME = "MemoryTokenBucket";

    // 用于存储每个key的令牌桶状态
    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean tryAcquire(RateLimiterContext context) {
        String key = context.getKey();
        long limit = context.getLimit();
        long period = context.getPeriod();
        TimeUnit timeUnit = context.getTimeUnit();
        // 根据key获取或创建一个新的令牌桶
        TokenBucket bucket = buckets.computeIfAbsent(key, k -> {
            long capacity = limit;
            // 计算每毫秒生成的令牌数，使用double保证精度
            double tokensPerMillisecond = (double) limit / timeUnit.toMillis(period);
            return new TokenBucket(capacity, tokensPerMillisecond);
        });

        // 尝试从桶中消费一个令牌
        return bucket.tryConsume();
    }

    @Override
    public String getAlgorithmName() {
        return ALGORITHM_NAME;
    }

    /**
     * 内部类，用于封装令牌桶的状态和逻辑
     */
    private static class TokenBucket {
        // 桶的容量
        private final long capacity;
        // 每毫秒生成的令牌数
        private final double tokensPerMillisecond;
        // 当前桶中的令牌数
        private double currentTokens;
        // 上次补充令牌的时间戳 (毫秒)
        private long lastRefillTimestamp;

        public TokenBucket(long capacity, double tokensPerMillisecond) {
            this.capacity = capacity;
            this.tokensPerMillisecond = tokensPerMillisecond;
            // 初始时，桶是满的
            this.currentTokens = capacity;
            this.lastRefillTimestamp = System.currentTimeMillis();
        }

        /**
         * 尝试消费一个令牌，整个过程需要线程安全
         * @return true 如果成功消费，false 如果令牌不足
         */
        public synchronized boolean tryConsume() {
            // 补充令牌
            refill();
            // 检查令牌是否足够
            if (currentTokens >= 1) {
                currentTokens--;
                return true;
            } else {
                return false;
            }
        }

        /**
         * 计算并补充令牌
         */
        private void refill() {
            long now = System.currentTimeMillis();
            // 计算自上次补充以来经过的时间
            long elapsedTime = now - lastRefillTimestamp;

            if (elapsedTime > 0) {
                // 计算这段时间内应生成的令牌数
                double generatedTokens = elapsedTime * tokensPerMillisecond;
                // 补充令牌，但不能超过桶的容量
                this.currentTokens = Math.min(capacity, currentTokens + generatedTokens);
                // 更新上次补充时间
                this.lastRefillTimestamp = now;
            }
        }
    }
}