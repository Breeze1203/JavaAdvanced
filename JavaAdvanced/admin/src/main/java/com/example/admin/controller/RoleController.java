package com.example.admin.controller;

import com.example.admin.model.Role;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.RoleService;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色的请求模块")
@RestController
public class RoleController {
    @Resource(name = "RoleService")
    RoleService roleService;

    @GetMapping("/getAllRole")
    @Operation(summary = "获取所有角色接口", description = "获取所有角色", method = "get")
    public List<Role> getAllROle() {
        return roleService.getAllRole();
    }

    /*
    添加角色
     */
    @Operation(summary = "添加角色接口", description = "添加角色", method = "post")
    @Parameter(name = "role", description = "角色json实体类", required = true)
    @CheckPermission(permission = "add_role")
    @PostMapping("/addRole")
    public StatusUtil addRole(@RequestBody Role role) {
        if (!role.getRole_name().startsWith("role_")) {
            role.setRole_name("role_" + role.getRole_name());
        }
        int i = roleService.addRole(role);
        if (i > 0) {
            return new StatusUtil(StatusMessage.ADD_SUCCESS.getMessage(), 200, null);
        }
        return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
    }

    /*
    删除角色
     */
    @Operation(method = "get", summary = "删除角色接口")
    @Parameter(name = "id", description = "根据角色id删除角色", required = true)
    @CheckPermission(permission = "delete_role")
    @GetMapping("/deleteRole")
    public StatusUtil deleteRole(@RequestParam("id") Integer id) {
        int i = roleService.deleteRole(id);
        if (i > 0) {
            return new StatusUtil(StatusMessage.DELETE_SUCCESS.getMessage(), 200, null);
        }
        return new StatusUtil(StatusMessage.EXISTS_USER.getMessage(), 500, null);
    }

    /*
    修改角色
     */
    @Operation(summary = "更新用户角色接口", method = "post", parameters = {@Parameter(name = "role", description = "角色json实体类")})
    @PostMapping("/updateRole")
    @CheckPermission(permission = "update_role")
    public StatusUtil updateRole(@RequestBody Role role) {
        Integer i = roleService.updateRole(role);
        if (i > 0) {
            return new StatusUtil(StatusMessage.UPDATE_SUCCESS.getMessage(), 200, null);
        }
        return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
    }
}
