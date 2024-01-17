package com.example.admin.config;


import com.example.admin.model.OperationData;
import com.example.admin.model.Organization;
import com.example.admin.model.Role;
import com.example.admin.model.User;
import com.example.admin.service.OperationDataService;
import com.example.admin.service.OrganizationService;
import com.example.admin.service.UserService;
import com.example.admin.util.DateUtil;
import com.example.admin.util.IpUtil;
import com.example.admin.util.Permission;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
/*
该类做日志处理
 */

@Aspect
@Component
public class AopConfig {
    @Resource(name = "OperationDataService")
    private OperationDataService operationDataService;

    @Resource(name = "UserService")
    private UserService userService;


    @AfterReturning(pointcut = "execution(* com.example.admin.controller.UserController.login(..))", returning = "result")
    private void afterLoginSuccess(JoinPoint joinPoint, Object result) {
        // 这个是方法返回值 将Object类型的参数转换为 StatusUtil 类型
        StatusUtil status = (StatusUtil) result;
        // 这个是获取方法的参数
        Object[] args = joinPoint.getArgs();
        if (status.getCode()==500) return;
        OperationData operationData = new OperationData();
        String u = args[0].toString();
        operationData.setUser(u);
        operationData.setDate(DateUtil.formatLog(new Date()));
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                String ipAddr = IpUtil.getClientIp((HttpServletRequest) arg);
                operationData.setType("用户登录");
                operationData.setOperation(u + "登录;ip地址为" + ipAddr);
                break;
            }
        }
        operationDataService.insertLog(operationData);
    }

    /*
    退出登录
     */
    @AfterReturning(pointcut = "execution(* com.example.admin.controller.UserController.loginOut(..))", returning = "result")
    private void afterLoginOutSuccess(JoinPoint joinPoint, Object result) {
        StatusUtil status = (StatusUtil) result;
        // 这个是获取方法的参数
        Object[] args = joinPoint.getArgs();
        if (status.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("退出登录");
        User u= userService.getUser((Integer) args[0]);
        operationData.setUser(u.getUsername());
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation(u.getUsername()+"退出登录");
        operationDataService.insertLog(operationData);
    }

    /*
    添加组织
     */
    @AfterReturning(pointcut = "execution(* com.example.admin.controller.OrganizationController.add(..))", returning = "result")
    private void addOrganizationLog(JoinPoint joinPoint, StatusUtil result) {
        // 这个是获取方法的参数
        Object[] args = joinPoint.getArgs();
        if (result.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("添加组织");
        Organization o = (Organization) args[0];
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation("添加" + o.getName());
        operationDataService.insertLog(operationData);
    }

    /*
    删除组织
     */
    @AfterReturning(pointcut = "execution(* com.example.admin.controller.OrganizationController.deleteById(..))", returning = "result")
    private void deleteOrganizationLog(JoinPoint joinPoint, StatusUtil result) {
        // 这个是获取方法的参数
        Object[] arg = joinPoint.getArgs();
        if (result.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("删除组织");
        Integer o = (Integer) arg[0];
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation("删除id为" + o + "的组织");
        operationDataService.insertLog(operationData);
    }

    /*
    添加用户
     */
    @AfterReturning(pointcut = "execution(* com.example.admin.controller.UserController.addUser(..))", returning = "result")
    private void addUser(JoinPoint joinPoint, StatusUtil result) {
        // 这个是获取方法的参数
        Object[] arg = joinPoint.getArgs();
        if (result.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("添加用户");
        User u= (User) arg[0];
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation("添加" + u.getUsername() + "用户");
        operationDataService.insertLog(operationData);
    }

    /*
    删除用户
     */
    @AfterReturning(pointcut = "execution(* com.example.admin.controller.UserController.deleteUser(..))", returning = "result")
    private void deleteUser(JoinPoint joinPoint, StatusUtil result) {
        // 这个是获取方法的参数
        Object[] arg = joinPoint.getArgs();
        if (result.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("删除用户");
        Integer i= (Integer) arg[0];
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation("删除id为" + i + "的用户");
        operationDataService.insertLog(operationData);
    }
    /*
    添加角色
     */
    @AfterReturning(pointcut = "execution(* com.example.admin.controller.RoleController.addRole(..))", returning = "result")
    private void addRole(JoinPoint joinPoint,StatusUtil result){
        // 这个是获取方法的参数
        Object[] arg = joinPoint.getArgs();
        if (result.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("添加角色");
        Role role= (Role) arg[0];
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation("添加" + role.getNameZh() + "角色");
        operationDataService.insertLog(operationData);
    }

    /*
    删除角色
     */
    @AfterReturning(pointcut = "execution(* com.example.admin.controller.RoleController.deleteRole(..))", returning = "result")
    private void deleteRole(JoinPoint joinPoint,StatusUtil result){
        // 这个是获取方法的参数
        Object[] arg = joinPoint.getArgs();
        if (result.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("删除角色");
        Integer rid= (Integer) arg[0];
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation("删除id为" + rid + "角色");
        operationDataService.insertLog(operationData);
    }
    /*
    修改权限
     */
    @AfterReturning(pointcut = "execution(* com.example.admin.controller.RoleAuthorizationController.updatePer(..))", returning = "result")
    private void updatePermission(JoinPoint joinPoint,StatusUtil result){
        // 这个是获取方法的参数
        Object[] arg = joinPoint.getArgs();
        if (result.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("修改权限");
        Permission permission = (Permission) arg[0];
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation("修改id为" + permission.getRid() + "角色权限,新的权限id为"+ Arrays.toString(permission.getAllId()));
        operationDataService.insertLog(operationData);
    }

    @AfterReturning(pointcut = "execution(* com.example.admin.controller.UserController.loginByPhone(..))",returning = "result")
    private void loginByPhone(JoinPoint joinPoint,StatusUtil result){
        Object[] args = joinPoint.getArgs();
        String phone= args[0].toString();
        if (result.getCode() == 500) return;
        OperationData operationData = new OperationData();
        operationData.setType("用户登录");
        operationData.setDate(DateUtil.formatLog(new Date()));
        operationData.setOperation("手机号为" + phone + "用户登录");
        operationDataService.insertLog(operationData);
    }
}
