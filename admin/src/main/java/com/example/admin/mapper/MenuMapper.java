package com.example.admin.mapper;


import com.example.admin.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "MenuMapper")
public interface MenuMapper {
    /*
    获取所有菜单
     */
    List<Menu> getAllMenus();
    /*
    获取一级菜单
     */
    List<Menu> getParentMenu();

    /*
    获取所有图标
     */
    List<String> getIcons();

    /*
    根据角色获取菜单列表
     */
    List<Menu> getMenuByRole(Integer rid);

    /*
    修改菜单信息
     */
    Integer updateMenuByMenuId(Menu menu);

    /*
    根据条件查询菜单角色
     */
    List<Menu> getMenuByCondition(Menu menu);
}
