package com.example.adminflow.service;

import com.example.adminflow.mapper.MenuMapper;
import com.example.adminflow.model.Menu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MenuService")
public class MenuService {
    @Resource(name = "MenuMapper")
    MenuMapper menuMapper;

    public List<Menu> getMenuByRole(Integer rid){
        return menuMapper.getMenuByRole(rid);
    }
}
