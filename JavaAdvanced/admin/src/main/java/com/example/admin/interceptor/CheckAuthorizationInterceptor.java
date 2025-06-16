package com.example.admin.interceptor;

import com.example.admin.util.JwtToken;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

@Component
public class CheckAuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        String authorization = request.getHeader("Authorization");
        Integer i = JwtToken.verifyToken(authorization);
        String s = redisTemplate.opsForValue().get(i + "token");
        if (i > 0 && s != null && authorization != null) {
            return true;
        }
        PrintWriter out = response.getWriter();
        StatusUtil statusUtil = new StatusUtil("请登录授权", 403, null);
        out.write(new ObjectMapper().writeValueAsString(statusUtil));
        out.flush(); //刷新输出流
        out.close(); // 关闭输出流
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
