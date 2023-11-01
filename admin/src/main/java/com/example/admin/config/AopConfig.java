package com.example.admin.config;


import com.example.admin.model.OperationData;
import com.example.admin.service.OperationDataService;
import com.example.admin.util.DateUtil;
import com.example.admin.util.IpUtil;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;
/*
该类做日志处理
 */

@Aspect
@Component
public class AopConfig {
    @Resource(name = "OperationDataService")
    OperationDataService operationDataService;

    @AfterReturning(pointcut = "execution(* com.example.admin.controller.UserController.login(..))",returning = "result")
    public void afterLoginSuccess(JoinPoint joinPoint, Object result){
        // 这个是方法返回值 将Object类型的参数转换为 StatusUtil 类型
        StatusUtil status = (StatusUtil) result;
        // 这个是获取方法的参数
        Object[] args = joinPoint.getArgs();
        if(status.getMessage().equals("用户名或密码错误")) return;
        OperationData operationData = new OperationData();
        String u=args[0].toString();
        operationData.setUser(u);
        operationData.setDate(DateUtil.formatLog(new Date()));
        for (Object arg : args) {
            if(arg instanceof HttpServletRequest){
                String ipAddr = IpUtil.getIpAddr((HttpServletRequest) arg);
                operationData.setType("用户登录");
                operationData.setOperation(u+"用户登录;ip地址为"+ipAddr);
                break;
            }
        }
        operationDataService.insetOperationLog(operationData);
    }

    @AfterReturning(pointcut = "execution(* com.example.admin.controller.UserController.loginOut(..))",returning = "result")
    public void afterLoginOutSuccess(JoinPoint joinPoint, Object result){
        StatusUtil status = (StatusUtil) result;
        // 这个是获取方法的参数
        Object[] args = joinPoint.getArgs();
        if(status.getCode()==500) return;
        OperationData operationData = new OperationData();
        operationData.setType("退出登录");
        String string = args[0].toString();
        operationData.setUser(string);
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation(string+"退出登录");
        operationDataService.insetOperationLog(operationData);
    }

}
