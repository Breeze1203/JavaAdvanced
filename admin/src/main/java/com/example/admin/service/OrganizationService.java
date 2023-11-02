package com.example.admin.service;

import com.example.admin.mapper.OrganizationMapper;
import com.example.admin.model.Organization;
import com.example.admin.model.User;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "OrganizationService")
public class OrganizationService {
    @Resource(name = "OrganizationMapper")
    OrganizationMapper organizationMapper;

    @Resource(name = "UserService")
    UserService userService;
    public List<Organization> getAllOrganization(Integer parentId){
        return organizationMapper.getAll(parentId);
    }

    public StatusUtil deleteById(Integer id){
        // 先查看该节点下是否有用户，如果有则无法删除
        List<User> userByOid = userService.getUserByOid(id);
        if(userByOid.isEmpty()){
            organizationMapper.deleteById(id);
            return new StatusUtil("删除成功",200,null);
        }else {
            return new StatusUtil("该节点下有用户，无法删除该节点",500,null);
        }
    }

    public Integer add(Organization organization) {
        return organizationMapper.addOrganization(organization);
    }
}
