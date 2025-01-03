package com.example.springbootconfiguration.controller;


import jakarta.annotation.Resource;
import com.trip.paygateway.infrastructure.config.User;
import com.trip.paygateway.infrastructure.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Resource
    public UserService userService;

    @Resource
    public User user;

    @GetMapping("/getUser")
    public String getUser(){
        String s = userService.userInfo();
        System.out.println(s);
        return user.toString();
    }
}
