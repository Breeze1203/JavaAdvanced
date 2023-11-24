package com.example.admin.controller;

import com.example.admin.model.Role;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.RoleService;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Resource(name = "RoleService")
    RoleService roleService;

    @GetMapping("/api/getAllRole")
    public List<Role> getAllROle() {
        return roleService.getAllRole();
    }

    @CheckPermission(permission = "add_role")
    @PostMapping("/api/addRole")
    public StatusUtil addRole(@RequestBody Role role){
        if(!role.getRole_name().startsWith("role_")){
            role.setRole_name("role_"+role.getRole_name());
        }
        int i = roleService.addRole(role);
        if(i>0){
            return new StatusUtil("添加成功",200,null);
        }
        return new StatusUtil("网络出现异常，请稍后再试",500,null);
    }

    @CheckPermission(permission = "delete_role")
    @GetMapping("/api/deleteRole")
    public StatusUtil deleteRole(@RequestParam("id")Integer id){
        int i = roleService.deleteRole(id);
        if(i>0){
            return new StatusUtil("删除成功",200,null);
        }
        return new StatusUtil("该角色下存在用户，无法删除",500,null);
    }

    @PostMapping("/api/updateRole")
    @CheckPermission(permission = "update_role")
    public StatusUtil updateRole(@RequestBody Role role){
        Integer i = roleService.updateRole(role);
        if(i>0){
            return new StatusUtil("修改成功",200,null);
        }
        return new StatusUtil("网络出现异常，请稍后再试",500,null);
    }
}
