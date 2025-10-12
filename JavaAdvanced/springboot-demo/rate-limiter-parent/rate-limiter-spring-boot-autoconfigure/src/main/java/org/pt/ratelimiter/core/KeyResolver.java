package org.pt.ratelimiter.core;

/**
 * @ClassName KeyResolver
 * @Author pt
 * @Description
 * @Date 2025/10/9 20:41
 **/

import jakarta.servlet.http.HttpServletRequest;

/**
 * 从请求中解析限流Key的解析器
 */
@FunctionalInterface
public interface KeyResolver {

    /**
     * 解析请求，返回用于限流的唯一键
     * @param request HTTP请求
     * @return 唯一键 (e.g., IP地址, 用户ID)
     */
    String resolve(HttpServletRequest request);
}