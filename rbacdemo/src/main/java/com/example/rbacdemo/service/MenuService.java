package com.example.rbacdemo.service;

import com.example.rbacdemo.mapper.MenuMapper;
import com.example.rbacdemo.model.menu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MenuService")
public class MenuService {
    @Resource(name = "MenuMapper")
    MenuMapper menuMapper;

    public List<menu> getMenuByRole(int rid){
        return menuMapper.getMenuByRole(rid);
    }
}
