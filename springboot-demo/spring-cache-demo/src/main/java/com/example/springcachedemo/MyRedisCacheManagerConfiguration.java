package com.example.springcachedemo;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * @ClassName MyRedisCacheManagerConfiguration
 * @Author pt
 * @Description
 * @Date 2025/1/21 15:06
 **/
@Configuration
public class MyRedisCacheManagerConfiguration {

        @Bean
        public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
            // 获取Properties中Redis的配置信息
            CacheProperties.Redis redisProperties = cacheProperties.getRedis();
            // 获取RedisCacheConfiguration的默认配置对象
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
            // 指定序列化器为GenericJackson2JsonRedisSerializer
            config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
            // 过期时间设置
            if (redisProperties.getTimeToLive() != null) {
                config = config.entryTtl(redisProperties.getTimeToLive());
            }
            // 替换前缀生成器（有前缀和无前缀）
            config = config.computePrefixWith(new CustomKeyPrefix());
            // 缓存空值配置
            if (!redisProperties.isCacheNullValues()) {
                config = config.disableCachingNullValues();
            }
            // 是否启用前缀
            if (!redisProperties.isUseKeyPrefix()) {
                config = config.disableKeyPrefix();
            }
            return config;
        }


}
