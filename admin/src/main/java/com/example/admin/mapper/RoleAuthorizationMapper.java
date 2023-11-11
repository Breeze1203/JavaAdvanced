package com.example.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository(value ="RoleAuthorizationMapper")
public interface RoleAuthorizationMapper {
    // 根据角色id(roleId)插入权限id
    int insertPerByRid(@Param("rId")Integer rId,@Param("aIds") Integer[] aIds);

    // 删除角色之前所具有的权限
    void deletePerByRid(@Param("rId")Integer rid);
}
