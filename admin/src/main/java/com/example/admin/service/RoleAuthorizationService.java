package com.example.admin.service;

import com.example.admin.mapper.RoleAuthorizationMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
该类处理角色与权限直接的关联
 */
@Service(value = "RoleAuthorizationService")
public class RoleAuthorizationService {

    @Resource(name = "RoleAuthorizationMapper")
    RoleAuthorizationMapper roleAuthorizationMapper;

    // 根据角色id插入权限
    @Transactional
    public int insertPerByRid(Integer rId,Integer[] aIds){
        roleAuthorizationMapper.deletePerByRid(rId);
        return roleAuthorizationMapper.insertPerByRid(rId,aIds);
    }
}
