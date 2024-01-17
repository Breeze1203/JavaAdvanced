package com.example.admin.service;

import com.example.admin.mapper.RoleMenuMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "RoleMenuService")
public class RoleMenuService implements RoleMenuMapper {
    @Resource(name = "RoleMenuMapper")
    private RoleMenuMapper roleMenuMapper;
    /*
    根据角色查询用户菜单权限
     */
    @Override
    public List<Integer> getMenuIdByRole(Integer rId) {
        return roleMenuMapper.getMenuIdByRole(rId);
    }

    @Transactional
    @Override
    public Long updateRoleMenu(String[] mIds, Integer rId) {
        Long l = deleteMenuByRole(rId);
        if(l>0){
            return roleMenuMapper.updateRoleMenu(mIds,rId);
        }
        return 0L;
    }


    @Override
    public Long deleteMenuByRole(Integer rid) {
        return roleMenuMapper.deleteMenuByRole(rid);
    }
}
