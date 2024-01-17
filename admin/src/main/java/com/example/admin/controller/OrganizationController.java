package com.example.admin.controller;

import com.example.admin.model.Organization;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.OrganizationService;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "部门模块请求接口")
@RequestMapping("/Organization")
public class OrganizationController {

    @Resource(name = "OrganizationService")
    OrganizationService organizationService;

    /*
    树状显示所有组织
     */
    @Operation(summary = "获取所有部门接口",description ="获取所有部门接口",method = "get")
    @GetMapping("/")
    public List<Organization> getAllOrganization() {
        return organizationService.getAll(-1);
    }

    /*
    根据id删除组织
     */
    @Operation(summary = "删除部门接口",description ="删除部门接口",method = "get")
    @Parameter(name = "id",description = "部门id",required = true)
    @GetMapping("/deleteById")
    @CheckPermission(permission = "delete_org")
    public StatusUtil deleteById(@RequestParam("id") Integer id) {
        Long l = organizationService.deleteOrgById(id);
        if (l != 0L) {
            return new StatusUtil(StatusMessage.DELETE_SUCCESS.getMessage(),200,null);
        }else {
            return new StatusUtil(StatusMessage.DELETE_FAILED.getMessage(),500,null);
        }
    }

    /*
    添加组织
     */
    @Operation(summary = "添加部门接口",description ="添加部门接口",method = "get")
    @Parameter(name = "organization",description = "部门json实体类",required = true)
    @PostMapping("/addOrganization")
    @CheckPermission(permission = "add_org")
    public StatusUtil add(@RequestBody Organization organization) {
        Integer add = organizationService.addOrganization(organization);
        if (add > 0) {
            return new StatusUtil(StatusMessage.ADD_SUCCESS.getMessage(), 200, null);
        } else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
        }
    }

    // 获取所有节点
    @Operation(summary = "获取所有部门接口",description ="获取所有部门接口",method = "get")
    @GetMapping("/getAllOrganization")
    public List<Organization> getAll() {
        return organizationService.getAllOrganization();
    }

}
