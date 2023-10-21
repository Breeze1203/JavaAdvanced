package com.example.adminflow.service;


import com.example.adminflow.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("RoleService")
public class RoleService {
    @Autowired
    UserRoleMapper userRoleMapper;

    public Integer getRoleId(int id){
        return userRoleMapper.getRoleIdById(id);
    }
}
