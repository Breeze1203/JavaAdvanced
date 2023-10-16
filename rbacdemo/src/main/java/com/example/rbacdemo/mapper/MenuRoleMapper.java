package com.example.rbacdemo.mapper;

import com.example.rbacdemo.model.menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MenuRoleMapper")
public interface MenuRoleMapper {
    public List<menu> getMenuByRoleId(@Param("roleId")int roleId);
}
