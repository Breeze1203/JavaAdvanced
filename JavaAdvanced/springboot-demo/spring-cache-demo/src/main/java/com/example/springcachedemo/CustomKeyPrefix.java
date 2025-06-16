package com.example.springcachedemo;

import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.util.Assert;


/**
 * @ClassName CustomKeyPrefix
 * @Author pt
 * @Description
 * @Date 2025/1/21 15:08
 **/
public class CustomKeyPrefix implements CacheKeyPrefix {

    @Override
    public String compute(String cacheName) {
        Assert.notNull(cacheName, "Prefix must not be null");
        return cacheName +":";
    }
}
