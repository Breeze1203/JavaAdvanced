package org.pt.ratelimiter.core;

/**
 * 限流算法策略接口
 */
public interface RateLimiterAlgorithm {
    /**
     * 尝试获取执行权限
     * @param context 包含所有必要信息的上下文对象
     * @return true 如果允许执行, false 如果被限流
     */
    boolean tryAcquire(RateLimiterContext context);

    /**
     * 获取算法的名称，用于在配置中指定
     * @return 算法名称
     */
    String getAlgorithmName();
}
