package com.example.admin.mapper;

import com.example.admin.model.Organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "OrganizationMapper")
public interface OrganizationMapper {
    List<Organization> getAll(@Param("parentId") Integer parentId);

    // 根据id删除组织
    void deleteById(@Param("id")Integer id);

    // 添加组织
    Integer addOrganization(Organization organization);
}
