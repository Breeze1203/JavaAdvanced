package com.example.admin.service;

import com.example.admin.mapper.AuthorizationMapper;
import com.example.admin.model.Authorization;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value ="AuthorizationService")
public class AuthorizationService {
    @Resource(name = "AuthorizationMapper")
    AuthorizationMapper authorizationMapper;

    // 获取所有权限
    public List<Authorization> getAll(){
        return authorizationMapper.getAllPermission();
    }

    // 根据用户角色获取可以访问的权限
    public List<Authorization> getPermissionByrId(Integer rid){
        return authorizationMapper.getPermissionByrId(rid);
    }
}
