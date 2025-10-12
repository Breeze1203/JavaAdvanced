package org.pt.ratelimiter.core;

/**
 * @ClassName RateLimiterContext
 * @Author pt
 * @Description
 * @Date 2025/10/9 21:13
 **/
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import java.util.concurrent.TimeUnit;

/**
 * 限流器上下文，用于在切面和策略实现之间传递所有必要的数据。
 */
@Data
@Builder
public class RateLimiterContext {

    /**
     * 限流的唯一键 (e.g., IP 地址, 用户 ID).
     * 由 KeyResolver 解析得出.
     */
    private String key;

    /**
     * 在时间窗口内允许的最大请求数.
     * 来自 @RateLimiter 注解.
     */
    private long limit;

    /**
     * 时间窗口的大小.
     * 来自 @RateLimiter 注解.
     */
    private long period;

    /**
     * 时间窗口的单位 (秒, 分钟等).
     * 来自 @RateLimiter 注解.
     */
    private TimeUnit timeUnit;

    /**
     * 原始的 HTTP 请求对象.
     * 提供此对象是为了将来可能的扩展，例如需要根据请求头或参数进行更复杂的限流判断.
     */
    private HttpServletRequest request;

}