package com.example.admin.controller;

import com.example.admin.model.Role;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.RoleService;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Resource(name = "RoleService")
    RoleService roleService;

    @GetMapping("/getAllRole")
    public List<Role> getAllROle() {
        return roleService.getAllRole();
    }

    /*
    添加角色
     */
    @CheckPermission(permission = "add_role")
    @PostMapping("/addRole")
    public StatusUtil addRole(@RequestBody Role role){
        if(!role.getRole_name().startsWith("role_")){
            role.setRole_name("role_"+role.getRole_name());
        }
        int i = roleService.addRole(role);
        if(i>0){
            return new StatusUtil(StatusMessage.ADD_SUCCESS.getMessage(), 200,null);
        }
        return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500,null);
    }

    /*
    删除角色
     */
    @CheckPermission(permission = "delete_role")
    @GetMapping("/deleteRole")
    public StatusUtil deleteRole(@RequestParam("id")Integer id){
        int i = roleService.deleteRole(id);
        if(i>0){
            return new StatusUtil(StatusMessage.DELETE_SUCCESS.getMessage(), 200,null);
        }
        return new StatusUtil(StatusMessage.EXISTS_USER.getMessage(),500,null);
    }

    /*
    修改角色
     */
    @PostMapping("/updateRole")
    @CheckPermission(permission = "update_role")
    public StatusUtil updateRole(@RequestBody Role role){
        Integer i = roleService.updateRole(role);
        if(i>0){
            return new StatusUtil(StatusMessage.UPDATE_SUCCESS.getMessage(), 200,null);
        }
        return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500,null);
    }
}
