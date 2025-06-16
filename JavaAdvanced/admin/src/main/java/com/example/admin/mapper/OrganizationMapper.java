package com.example.admin.mapper;

import com.example.admin.model.Organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "OrganizationMapper")
public interface OrganizationMapper {


    /*
    根据id删除组织
     */
    Long deleteOrgById(@Param("id")Integer id);

    /*
    添加组织
     */
    Integer addOrganization(Organization organization);

    /*
    查询所有组织
     */
    List<Organization> getAllOrganization();

    /*
    根据id查询组织
     */
    Organization getOrganizationById(@Param("id") Integer id);

    Integer updateOrganization(Organization organization);
}
