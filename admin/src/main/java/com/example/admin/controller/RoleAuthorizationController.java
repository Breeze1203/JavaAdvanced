package com.example.admin.controller;


import com.example.admin.permission.CheckPermission;
import com.example.admin.service.RoleAuthorizationService;
import com.example.admin.util.Permission;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


@Tag(name = "角色权限模块")
@RestController
public class RoleAuthorizationController {
    @Resource(name = "RoleAuthorizationService")
    RoleAuthorizationService roleAuthorizationService;

    /*
    更改用户操作权限
     */
    @Operation(description = "更改用户操作权限",summary = "更改用户操作权限接口",method = "post")
    @Parameter(name = "permission",description = "角色id及角色操作权限id")
    @PostMapping("/updatePermission")
    @CheckPermission(permission = "update_per")
    public StatusUtil updatePer(@RequestBody Permission permission){
        int i = roleAuthorizationService.insertPerByRid(permission.getRid(), permission.getAllId());
        if(i>0){
            return new StatusUtil(StatusMessage.UPDATE_SUCCESS.getMessage(), 200,null);
        }
        return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(),200,null);
    }
}
