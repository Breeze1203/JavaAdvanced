package org.pt.ratelimiter.core.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.pt.ratelimiter.core.KeyResolver;
import org.springframework.stereotype.Component;

/**
 * @ClassName IpKeyResolver
 * @Author pt
 * @Description  IP 地址 Key 解析器
 * @Date 2025/10/9 20:47
 **/
@Component(value = "ipKeyResolver")
public class IpKeyResolver implements KeyResolver {
        @Override
        public String resolve(HttpServletRequest request) {
            return request.getRemoteAddr();
        }
}
