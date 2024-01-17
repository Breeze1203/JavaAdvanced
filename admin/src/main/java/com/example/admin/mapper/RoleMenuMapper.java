package com.example.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository(value = "RoleMenuMapper")
public interface RoleMenuMapper {
    /*
    根据角色获取用户菜单权限
     */
    List<Integer> getMenuIdByRole(@Param("rId") Integer rId);

    /*
    更新角色菜单权限
     */
    Long updateRoleMenu(@Param("mIds")String[] mIds, @Param("rId") Integer rId);


    /*
        删除指定角色菜单权限
         */
    Long deleteMenuByRole(@Param("rId") Integer rId);
}
