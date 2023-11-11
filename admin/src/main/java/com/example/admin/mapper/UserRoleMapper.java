package com.example.admin.mapper;

import com.example.admin.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "UserRoleMapper")
public interface UserRoleMapper {
    // 根据用户id查询角色id
    Integer getRoleByUser(@Param("id")Integer id);

    // 根据用户id修改其角色
    int updateRoleById(@Param("rid")Integer rid,@Param("id")Integer id);

    // 插入角色与权限关联数据
    int insertRole(@Param("rid")Integer rid,@Param("id")Integer id);

    //查看当前用户有没有角色
    Integer getRole(@Param("id")Integer id);

    // 查看当前角色有没有用户，如果有用户，则无法删除角色
    List<User> getUserByRole(@Param("rid")Integer rid);

    // 删除已删除用户关联的角色
    Integer deleteRoleById(@Param("id")Integer id);
}
