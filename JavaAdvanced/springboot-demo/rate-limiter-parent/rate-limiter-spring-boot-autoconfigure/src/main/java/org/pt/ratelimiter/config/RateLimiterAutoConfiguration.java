package org.pt.ratelimiter.config;

import org.pt.ratelimiter.aspect.RateLimiterAspect;
import org.pt.ratelimiter.core.impl.*;
import org.pt.ratelimiter.exception.GlobalRateLimitExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * @ClassName RateLimiterAutoConfiguration
 * @Author pt
 * @Description
 * @Date 2025/10/9 20:58
 **/

@Configuration
@EnableConfigurationProperties(RateLimiterProperties.class)
@ConditionalOnProperty(prefix = "rate-limiter", name = "enabled", havingValue = "true")
public class RateLimiterAutoConfiguration {

    private final RateLimiterProperties properties;

    public RateLimiterAutoConfiguration(RateLimiterProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterAspect rateLimiterAspect(ApplicationContext applicationContext) {
        return new RateLimiterAspect(applicationContext, properties.getDefaultAlgorithm(), properties.getDefaultKeyResolver());
    }

    @Bean
    @ConditionalOnMissingBean(name = "ipKeyResolver")
    public IpKeyResolver ipKeyResolver() {
        return new IpKeyResolver();
    }

    @Bean
    @ConditionalOnMissingBean(name = MemoryTokenBucketRateLimiter.ALGORITHM_NAME)
    public MemoryTokenBucketRateLimiter tokenBucketRateLimiter() {
        return new MemoryTokenBucketRateLimiter();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalRateLimitExceptionHandler globalRateLimitExceptionHandler() {
        return new GlobalRateLimitExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = RedisTokenBucketRateLimiter.ALGORITHM_NAME)
    public RedisTokenBucketRateLimiter redisTokenBucketRateLimiter(RedisTemplate<String, String> redisTemplate, RedisScript<Long> rateLimiterLuaScript) {
        return new RedisTokenBucketRateLimiter(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(name = SlidingWindowRateLimiter.ALGORITHM_NAME)
    public SlidingWindowRateLimiter slidingWindowRateLimiter(RedisTemplate<String, String> redisTemplate) {
      return new SlidingWindowRateLimiter(redisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(name = LeakyBucketRateLimiter.ALGORITHM_NAME)
    public LeakyBucketRateLimiter leakyBucketRateLimiter(RedisTemplate<String, String> redisTemplate) {
        return new LeakyBucketRateLimiter(redisTemplate);
    }
}
