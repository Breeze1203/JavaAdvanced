package com.example.adminflow.controller;

import com.example.adminflow.model.Menu;
import com.example.adminflow.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource(name = "MenuService")
    private MenuService menuService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/init")
    private List<Menu> getAllMenu(@RequestParam("id") int id) {
        String s = redisTemplate.opsForValue().get("role_" + id);
        if (s != null) {
            return menuService.getMenuByRole(Integer.valueOf(s));
        }
        return null;
    }
}
