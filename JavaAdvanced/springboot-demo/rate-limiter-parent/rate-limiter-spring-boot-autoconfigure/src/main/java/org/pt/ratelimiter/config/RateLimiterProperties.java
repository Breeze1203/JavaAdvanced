package org.pt.ratelimiter.config;

import lombok.Data;
import org.pt.ratelimiter.core.impl.MemoryTokenBucketRateLimiter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName RateLimiterProperties
 * @Author pt
 * @Description 配置属性类
 * @Date 2025/10/9 20:55
 **/
@Data
@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterProperties {
    /**
     * 是否启用，默认为true
     */
    private boolean enabled = true;

    /**
     * 全局默认的限流算法Bean名称
     */
    private String defaultAlgorithm = MemoryTokenBucketRateLimiter.ALGORITHM_NAME;

    /**
     * 全局默认的Key解析器Bean名称
     */
    private String defaultKeyResolver = "ipKeyResolver";

}
