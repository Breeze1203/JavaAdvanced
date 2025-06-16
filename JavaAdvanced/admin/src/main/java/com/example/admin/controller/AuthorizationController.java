package com.example.admin.controller;

import com.example.admin.model.Authorization;
import com.example.admin.service.AuthorizationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorizationController {

    @Resource(name = "AuthorizationService")
    private AuthorizationService authorizationService;

    /*
    获取所有操作权限数据
     */
    @GetMapping("/getAllPermByPage")
    public List<Authorization> getAllPermissionByPage(@RequestParam("page")Integer page,@RequestParam("size")Integer size){
        return authorizationService.getAllPermissionByPage(page,size);
    }

    @GetMapping("/getPermissionByrId")
    public List<Authorization> getPermissionByrId(@RequestParam("rid")Integer rid){
        return authorizationService.getPermissionByrId(rid);
    }
    /*
    获取操作权限数据条数
     */
    @GetMapping("/getPermissionCount")
    public Long getPermissionCount(){
        return authorizationService.getPermissionCount();
    }

    /*
    获取所有操作权限
     */
    @GetMapping("/getAllPermissions")
    public List<Authorization> getPermissions(){
        return authorizationService.getPermissions();
    }
}
