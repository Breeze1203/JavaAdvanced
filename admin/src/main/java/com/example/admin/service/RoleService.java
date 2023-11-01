package com.example.admin.service;


import com.example.admin.mapper.RoleMapper;
import com.example.admin.mapper.UserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("RoleService")
public class RoleService {
    @Autowired
    UserRoleMapper userRoleMapper;

    @Resource(name = "RoleMapper")
    RoleMapper roleMapper;

    public Integer getRoleId(int id){
        return userRoleMapper.getRoleIdById(id);
    }

    public String getRoleById(Integer id){
        return roleMapper.getRoleById(id);
    }
}
