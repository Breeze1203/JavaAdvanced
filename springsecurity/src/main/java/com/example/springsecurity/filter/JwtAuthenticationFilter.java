package com.example.springsecurity.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        System.out.println(header);
        // 在这里执行身份认证逻辑
        if (!Objects.isNull(header)) {
            // 身份认证通过，继续处理该请求
            filterChain.doFilter(request, response);
        } else {
            // 身份认证失败，可以返回错误响应或者进行其他处理
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
