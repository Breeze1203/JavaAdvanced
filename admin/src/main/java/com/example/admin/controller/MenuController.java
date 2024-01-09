package com.example.admin.controller;

import com.example.admin.model.Menu;
import com.example.admin.service.MenuService;
import com.example.admin.service.RoleMenuService;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    @Resource(name = "MenuService")
    private MenuService menuService;

    @Resource(name = "RoleMenuService")
    private RoleMenuService roleMenuService;

    /*
    获取所有菜单展示
     */
    @GetMapping("/getAllMenus")
    private List<Menu> getAllMenu() {
        return menuService.getAllMenus();
    }

    /*
    获取一级菜单
     */
    @GetMapping("/getParentMenu")
    private List<Menu> getParentMenu() {
        return menuService.getParentMenu();
    }

    /*
    获取所有icon
     */
    @GetMapping("/getAllIcons")
    private List<String> getIcons() {
        return menuService.getIcons();
    }

    /*
    根据角色获取菜单
     */
    @GetMapping("/getMenusByRole/{rid}")
    private List<Menu> getMenuByRole(@PathVariable("rid") Integer rid) {
        return menuService.getMenuByRole(rid);
    }

    /*
    根据角色获取可选中id
     */
    @GetMapping("/getMenusChecked/{rid}")
    private List<Integer> getMenuIdByRole(@PathVariable("rid") Integer rid) {
        return roleMenuService.getMenuIdByRole(rid);
    }

    /*
    修改角色菜单权限
     */
    @GetMapping("/updateRoleMenuId")
    private StatusUtil updateRoleMenuId(@RequestParam("mIds") String mIds,@RequestParam("rId")Integer rId) {
        String[] split = mIds.split(",");
        Long l = roleMenuService.updateRoleMenu(split,rId);
        if (l > 0) {
            return new StatusUtil(StatusMessage.UPDATE_SUCCESS.getMessage(), 200, null);
        } else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
        }
    }
}
