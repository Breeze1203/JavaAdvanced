package com.example.admin.controller;


import com.example.admin.permission.CheckPermission;
import com.example.admin.service.RoleAuthorizationService;
import com.example.admin.util.Permission;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@RestController
public class RoleAuthorizationController {
    @Resource(name = "RoleAuthorizationService")
    RoleAuthorizationService roleAuthorizationService;

    @PostMapping("/api/updatePermission")
    @CheckPermission(permission = "update_per")
    public StatusUtil updatePer(@RequestBody Permission permission){
        int i = roleAuthorizationService.insertPerByRid(permission.getRid(), permission.getAllId());
        if(i>0){
            return new StatusUtil("修改成功",200,null);
        }
        return new StatusUtil("网络出现异常，请稍后再试",200,null);
    }
}
