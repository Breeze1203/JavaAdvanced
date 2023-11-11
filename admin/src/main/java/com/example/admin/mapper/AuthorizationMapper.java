package com.example.admin.mapper;

import com.example.admin.model.Authorization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "AuthorizationMapper")
public interface AuthorizationMapper {
    List<Authorization> getAllPermission();

    List<Authorization> getPermissionByrId(@Param("roleId")Integer rid);
}

