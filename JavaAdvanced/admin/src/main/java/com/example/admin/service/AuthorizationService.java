package com.example.admin.service;

import com.example.admin.mapper.AuthorizationMapper;
import com.example.admin.model.Authorization;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "AuthorizationService")
public class AuthorizationService implements AuthorizationMapper{
    @Resource(name = "AuthorizationMapper")
    AuthorizationMapper authorizationMapper;

    /*
    分页获取所有操作权限
    */
    @Override
    public List<Authorization> getAllPermissionByPage(Integer page, Integer size) {
        page = (page - 1) * size;
        return authorizationMapper.getAllPermissionByPage(page, size);
    }

    /*
    根据用户角色获取可以访问的操作权限
     */
    @Override
    public List<Authorization> getPermissionByrId(Integer rid) {
        return authorizationMapper.getPermissionByrId(rid);
    }

    /*
    查询操作权限条数
     */
    @Override
    public Long getPermissionCount() {
        return authorizationMapper.getPermissionCount();
    }

    /*
    获取所有操作权限
     */
    @Override
    public List<Authorization> getPermissions() {
        return authorizationMapper.getPermissions();
    }
}
