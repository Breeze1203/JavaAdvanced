package com.example.adminflow.mapper;

import com.example.adminflow.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MenuRoleMapper")
public interface MenuRoleMapper {
    public List<Menu> getMenuByRoleId(@Param("roleId")int roleId);
}
