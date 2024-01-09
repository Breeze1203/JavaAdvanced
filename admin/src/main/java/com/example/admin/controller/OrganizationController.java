package com.example.admin.controller;

import com.example.admin.model.Organization;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.OrganizationService;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Organization")
public class OrganizationController {

    @Resource(name = "OrganizationService")
    OrganizationService organizationService;
    /*
    树状显示所有组织
     */
    @GetMapping("/")
    public List<Organization> getAllOrganization(){
        return organizationService.getAllOrganization(-1);
    }

    /*
    根据id删除组织
     */
    @GetMapping("/deleteById")
    @CheckPermission(permission = "delete_org")
    public StatusUtil deleteById(@RequestParam("id")Integer id){
        return organizationService.deleteById(id);
    }

    /*
    添加组织
     */
    @PostMapping("/addOrganization")
    @CheckPermission(permission = "add_org")
    public StatusUtil add(@RequestBody Organization organization){
        Integer add = organizationService.add(organization);
        if(add>0){
            return new StatusUtil(StatusMessage.ADD_SUCCESS.getMessage(), 200,null);
        }else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500,null);
        }
    }

    // 获取所有节点
    @GetMapping("/getAllOrganization")
    public List<Organization> getAll(){
        return organizationService.getAll();
    }

}
