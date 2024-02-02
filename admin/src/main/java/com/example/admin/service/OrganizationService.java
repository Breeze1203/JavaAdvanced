package com.example.admin.service;

import com.example.admin.mapper.OrganizationMapper;
import com.example.admin.model.Organization;
import com.example.admin.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "OrganizationService")
public class OrganizationService implements OrganizationMapper{
    @Resource(name = "OrganizationMapper")
    private OrganizationMapper organizationMapper;

    @Resource(name = "UserService")
    private UserService userService;


    /*
        根据id删除组织
         */
    public Long deleteOrgById(Integer id){
        // 先查看该节点下是否有用户，如果有则无法删除
        List<User> userByOid = userService.getUserByoId(id);
        if(userByOid.isEmpty()){
            return organizationMapper.deleteOrgById(id);
        }else {
            return 0L;
        }
    }

    /*
    添加组织
     */
    public Integer addOrganization(Organization organization) {
        return organizationMapper.addOrganization(organization);
    }

    /*
    获取所有组织
     */
    public List<Organization> getAllOrganization(){
        return organizationMapper.getAllOrganization();
    }

    /*
    根据id获取组织
     */
    @Override
    public Organization getOrganizationById(Integer id) {
        return organizationMapper.getOrganizationById(id);
    }

    /*
    树状展示所有组织
     */
    public Organization getTree() {
        List<Organization> allOrganization = organizationMapper.getAllOrganization();
        return Organization.covertToTree(allOrganization);
    }

    @Override
    public Integer updateOrganization(Organization organization) {
        return organizationMapper.updateOrganization(organization);
    }
}
