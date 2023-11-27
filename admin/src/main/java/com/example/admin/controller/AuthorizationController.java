package com.example.admin.controller;

import com.example.admin.model.Authorization;
import com.example.admin.service.AuthorizationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthorizationController {

    @Resource(name = "AuthorizationService")
    AuthorizationService authorizationService;

    // 获取所有
    @GetMapping("/api/getAllPermission")
    public List<Authorization> getAll(){
        return authorizationService.getAll();
    }

    @GetMapping("/api/getPermissionByrId")
    public List<Integer> getByRid(@RequestParam("rid")Integer rid){
        List<Authorization> all = authorizationService.getPermissionByrId(rid);
        List<Integer> permission=new ArrayList<>();
        for(Authorization a:all){
            permission.add(a.getId());
        }
        return permission;
    }

}
