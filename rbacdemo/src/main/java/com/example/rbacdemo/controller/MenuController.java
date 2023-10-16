package com.example.rbacdemo.controller;

import com.example.rbacdemo.model.menu;
import com.example.rbacdemo.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
   @Resource(name = "MenuService")
   private MenuService menuService;

   @GetMapping("/init")
    private List<menu> getAllMenu(){
       return menuService.getMenuByRole(3);
   }
}
