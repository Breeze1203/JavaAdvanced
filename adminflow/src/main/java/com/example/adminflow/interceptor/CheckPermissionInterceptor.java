package com.example.adminflow.interceptor;

import com.example.adminflow.permission.CheckPermission;
import com.example.adminflow.util.StatusUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckPermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前进行拦截逻辑的编写 返回true表示继续执行请求，返回false表示拦截请求
        if(handler instanceof HandlerMethod){
            HandlerMethod handMethod = (HandlerMethod) handler;
            // 获取方法对象
            Method method = handMethod.getMethod();
            // 获取方法对象上的注解对象
            CheckPermission annotation = method.getAnnotation(CheckPermission.class);
            if(annotation!=null){
                String permission = annotation.permission();
                //测试数据
                List<String> userPermissionList = new ArrayList<>();
                userPermissionList.add("User:addUser");
                userPermissionList.add("User:deleteUser");
                userPermissionList.add("User:updateUser");
                userPermissionList.add("User:pageList");
                //开始判断 (也可以拦截器抛出异常，以中断请求处理)
                if(!userPermissionList.contains(permission)){
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out=response.getWriter();
                    StatusUtil statusUtil = new StatusUtil("你暂无访问权限",null);
                    out.write(new ObjectMapper().writeValueAsString(statusUtil));
                    out.flush(); //刷新输出流
                    out.close(); // 关闭输出流
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
