package com.example.admin.service;

import com.example.admin.mapper.MenuMapper;
import com.example.admin.model.Menu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "MenuService")
public class MenuService implements MenuMapper {
    @Resource(name = "MenuMapper")
    private MenuMapper mapper;

    /*
    查询所有菜单
     */
    @Override
    public List<Menu> getAllMenus() {
        return mapper.getAllMenus();
    }

    /*
    查询一级菜单
     */
    @Override
    public List<Menu> getParentMenu() {
        return mapper.getParentMenu();
    }

    /*
    查询所有菜单
     */
    @Override
    public List<String> getIcons() {
        return mapper.getIcons();
    }

    /*
    根据角色获取菜单
     */
    @Override
    public List<Menu> getMenuByRole(Integer rid) {
        return mapper.getMenuByRole(rid);
    }

    /*
    修改菜单信息
     */
    @Override
    public Integer updateMenuByMenuId(Menu menu) {
        return mapper.updateMenuByMenuId(menu);
    }

    @Override
    public List<Menu> getMenuByCondition(Menu menu) {
        return mapper.getMenuByCondition(menu);
    }
}
