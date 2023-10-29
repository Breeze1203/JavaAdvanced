package com.example.adminflow.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CheckPermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前进行拦截逻辑的编写 返回true表示继续执行请求，返回false表示拦截请求
//        if (handler instanceof HandlerMethod handMethod) {
//            // 获取方法对象
//            Method method = handMethod.getMethod();
//            // 获取方法对象上的注解对象
//            CheckPermission annotation = method.getAnnotation(CheckPermission.class);
//            if (annotation != null) {
//                String permission = annotation.permission();
//                //测试数据
//                List<String> userPermissionList = new ArrayList<>();
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
