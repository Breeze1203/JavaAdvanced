package com.example.admin.controller;

import com.example.admin.model.Organization;
import com.example.admin.service.OrganizationService;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Organization")
public class OrganizationController {

    @Resource(name = "OrganizationService")
    OrganizationService organizationService;

    @GetMapping("/")
    public List<Organization> getAllOrganization(){
        return organizationService.getAllOrganization(-1);
    }

    // 根据id删除节点
    @GetMapping("/deleteById")
    public StatusUtil deleteById(@RequestParam("id")Integer id){
        return organizationService.deleteById(id);
    }

    // 添加节点
    @PostMapping("/addOrganization")
    public StatusUtil add(@RequestBody Organization organization){
        Integer add = organizationService.add(organization);
        if(add>0){
            return new StatusUtil("添加成功",200,null);
        }else {
            return new StatusUtil("添加失败",500,null);
        }
    }

}
