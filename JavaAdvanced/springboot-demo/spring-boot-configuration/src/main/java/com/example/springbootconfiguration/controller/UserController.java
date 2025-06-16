package com.example.springbootconfiguration.controller;


import jakarta.annotation.Resource;
import com.trip.paygateway.infrastructure.config.User;
import com.trip.paygateway.infrastructure.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {

    @Resource
    public UserService userService;

    @Resource
    public User user;

    @GetMapping("/getUser")
    public String getUser(){
        user.setDate(new Date());
        return user.toString();
    }
}
