package com.example.adminflow.config;


import com.example.adminflow.model.LoginData;
import com.example.adminflow.service.LoginDataService;
import com.example.adminflow.util.DateUtil;
import com.example.adminflow.util.IpUtil;
import com.example.adminflow.util.StatusUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class AopConfig {
    @Resource(name = "LogDataService")
    LoginDataService loginDataService;

    @AfterReturning(pointcut = "execution(* com.example.adminflow.controller.UserController.login(..))",returning = "result")
    public void afterLoginSuccess(JoinPoint joinPoint, Object result){
        // 这个是方法返回值 将Object类型的参数转换为 StatusUtil 类型
        StatusUtil status = (StatusUtil) result;
        // 这个是获取方法的参数
        Object[] args = joinPoint.getArgs();
        if(status.getMessage().equals("用户名或密码错误")) return;
        LoginData loginData = new LoginData();
        String u=args[0].toString();
        loginData.setLogin_user(u);
        loginData.setLogin_Date(DateUtil.formatLog(new Date()));
        for (Object arg : args) {
            if(arg instanceof HttpServletRequest){
                String ipAddr = IpUtil.getIpAddr((HttpServletRequest) arg);
                loginData.setOperation_log(u+"用户登录;ip地址为"+ipAddr);
                break;
            }
        }
        loginDataService.insetLoginLog(loginData);
    }

}
