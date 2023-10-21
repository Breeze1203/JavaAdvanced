package com.example.adminflow.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("UserRoleMapper")
public interface UserRoleMapper {
//    根据用户id查询roleId
    Integer getRoleIdById(@Param("id")int id);
}
