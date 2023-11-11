package com.example.admin.service;


import com.example.admin.mapper.RoleMapper;
import com.example.admin.mapper.UserRoleMapper;
import com.example.admin.model.Role;
import com.example.admin.model.User;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "RoleService")
public class RoleService {
    @Resource(name = "UserRoleMapper")
    UserRoleMapper userRoleMapper;

    @Resource(name = "RoleMapper")
    RoleMapper roleMapper;

    // 根据用户id获取其角色id
    public Integer getRidByuId(Integer id) {
        return userRoleMapper.getRoleByUser(id);
    }

    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    public int addRole(Role role) {
        return roleMapper.addRole(role);
    }

    // 根据id删除角色
    public int deleteRole(Integer id) {
        List<User> user = userRoleMapper.getUserByRole(id);
        // 当前角色下有用户，无法删除
        if(!user.isEmpty()){
            return -1;
        }
        return roleMapper.deleteRoleById(id);
    }

    // 根据用户id修改其角色
    public int updateUserById(Integer rid, Integer id) {
        Integer role = userRoleMapper.getRole(id);
        if (role != null) {
            return userRoleMapper.updateRoleById(rid, id);
        }
        return userRoleMapper.insertRole(rid, id);
    }

    public Integer updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    // 删除已删除用户关联的角色
    public Integer deleteRoleById(Integer id){
        return userRoleMapper.deleteRoleById(id);
    }
}
