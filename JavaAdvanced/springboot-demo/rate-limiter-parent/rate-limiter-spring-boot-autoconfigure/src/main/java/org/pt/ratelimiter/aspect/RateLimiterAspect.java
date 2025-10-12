package org.pt.ratelimiter.aspect;

import lombok.RequiredArgsConstructor;
import org.pt.ratelimiter.annotation.RateLimiter;
import org.pt.ratelimiter.core.KeyResolver;
import org.pt.ratelimiter.core.RateLimiterAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.pt.ratelimiter.core.RateLimiterContext;
import org.pt.ratelimiter.exception.RateLimitException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Objects;

/**
 * @ClassName RateLimiterAspect
 * @Author pt
 * @Description
 * @Date 2025/10/9 20:48
 **/
@Aspect
@RequiredArgsConstructor
public class RateLimiterAspect {

    private final ApplicationContext applicationContext;
    private final String defaultAlgorithm;
    private final String defaultKeyResolver;

    @Around("@annotation(rateLimiter)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取限流算法
        String algorithmName = StringUtils.hasText(rateLimiter.algorithm()) ? rateLimiter.algorithm() : defaultAlgorithm;
        RateLimiterAlgorithm algorithm = applicationContext.getBean(algorithmName, RateLimiterAlgorithm.class);

        // 获取Key解析器
        String keyResolverName = StringUtils.hasText(rateLimiter.keyResolver()) ? rateLimiter.keyResolver() : defaultKeyResolver;
        KeyResolver keyResolver = applicationContext.getBean(keyResolverName, KeyResolver.class);

        // 构建上下文
        String key = keyResolver.resolve(request);
        RateLimiterContext context = RateLimiterContext.builder()
                .key(key)
                .limit(rateLimiter.limit())
                .period(rateLimiter.period())
                .timeUnit(rateLimiter.timeUnit())
                .request(request)
                .build();

        // 执行限流判断
        if (!algorithm.tryAcquire(context)) {
            throw new RateLimitException(rateLimiter.message());
        }
        return joinPoint.proceed();
    }
}