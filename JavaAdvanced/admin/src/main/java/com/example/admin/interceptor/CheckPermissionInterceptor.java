package com.example.admin.interceptor;

import com.example.admin.model.Authorization;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.AuthorizationService;
import com.example.admin.service.RoleService;
import com.example.admin.util.JwtToken;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckPermissionInterceptor implements HandlerInterceptor {

    @Resource(name = "AuthorizationService")
    AuthorizationService authorizationService;

    @Resource(name = "RoleService")
    RoleService roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前进行拦截逻辑的编写 返回true表示继续执行
        if (handler instanceof HandlerMethod handMethod) {
            String authorization = request.getHeader("Authorization");
            Integer i = JwtToken.verifyToken(authorization);
            Integer rid = roleService.getRidByuId(i);
            List<Authorization> per = authorizationService.getPermissionByrId(rid);
            List<String> p=new ArrayList<>();
            for (Authorization r:per){
                p.add(r.getName());
            }
            // 获取方法对象
            Method method = handMethod.getMethod();
            // 获取方法对象上的注解对象
            CheckPermission annotation = method.getAnnotation(CheckPermission.class);
            if (annotation != null) {
                String permission = annotation.permission();
                if (p.contains(permission)) {
                    return true;
                }
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                StatusUtil statusUtil = new StatusUtil(StatusMessage.ILLEGAL_PERMISSIONS.getMessage(), 401, null);
                out.write(new ObjectMapper().writeValueAsString(statusUtil));
                out.flush(); //刷新输出流
                out.close(); // 关闭输出流
                return false;
            }
            return true;
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
