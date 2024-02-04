package com.example.springsecurity.controller;


import com.example.springsecurity.mapper.MenuMapper;
import com.example.springsecurity.model.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class TestController {

    @Resource(name = "MenuMapper")
    private MenuMapper mapper;

    @GetMapping("/login")
    public List<Menu> hello() {
        return mapper.getMenuByRole();
    }
}
