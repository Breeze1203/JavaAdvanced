package com.example.admin.mapper;

import com.example.admin.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "RoleMapper")
public interface RoleMapper {
    /*
    根据角色id查询用户角色
     */
   String getRoleById(@Param("id") Integer id);

   /*
   获取所有角色
    */
    List<Role> getAllRole();

    /*
    添加角色
     */
    int addRole(Role role);

    /*
    删除角色
     */
    int deleteRoleById(Integer id);

    /*
    更新角色
     */
    Integer updateRole(Role role);
}
