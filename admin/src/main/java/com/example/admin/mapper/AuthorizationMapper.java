package com.example.admin.mapper;

import com.example.admin.model.Authorization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "AuthorizationMapper")
public interface AuthorizationMapper {
    List<Authorization> getAllPermissionByPage(@Param("page")Integer page,@Param("size")Integer size);

    /*
    根据角色查询用户操作权限
     */
    List<Authorization> getPermissionByrId(@Param("roleId")Integer rid);

    /*
    查询操作权限条数
     */
    Long getPermissionCount();

    List<Authorization> getPermissions();
}

