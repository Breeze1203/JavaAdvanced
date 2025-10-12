package org.pt.ratelimiter.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RateLimiter
 * @Author pt
 * @Description
 * @Date 2025/10/9 20:43
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

        /**
         * 允许的请求数
         */
        long limit() default 10;

        /**
         * 时间窗口
         */
        long period() default 1;

        /**
         * 时间单位，默认为秒
         */
        TimeUnit timeUnit() default TimeUnit.SECONDS;

        /**
         * 使用的限流算法Bean的名称。
         * 如果为空，则使用配置文件中的默认算法。
         */
        String algorithm() default "";

        /**
         * 使用的Key解析器Bean的名称。
         * 如果为空，则使用配置文件中的默认解析器。
         */
        String keyResolver() default "";

        /**
         * 当请求被拒绝时的提示信息
         */
        String message() default "Too many requests, please try again later.";
}
