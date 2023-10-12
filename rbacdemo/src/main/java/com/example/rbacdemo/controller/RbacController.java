package com.example.rbacdemo.controller;

import com.example.rbacdemo.permission.CheckPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RbacController {

    @GetMapping("/login")
    public String login(){
        return "登录成功";
    }

    @GetMapping("/hello")
    @CheckPermission(permission = "user")
    public String hello(){
        return "欢迎你";
    }
}
