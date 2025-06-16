package com.example.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository(value ="RoleAuthorizationMapper")
public interface RoleAuthorizationMapper {
    /*
    根据角色id(roleId)插入操作权限id
     */
    int insertPerByRid(@Param("rId")Integer rId,@Param("aIds") Integer[] aIds);

    /*
    根据id删除操作权限
     */
    void deletePerByRid(@Param("rId")Integer rid);
}
