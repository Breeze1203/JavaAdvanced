package com.example.adminflow.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("RoleMapper")
public interface RoleMapper {
    // 根据角色id查询用户角色
   String getRoleById(@Param("id") Integer id);
}
