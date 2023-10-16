package com.example.rbacdemo.service;

import com.example.rbacdemo.mapper.UserMapper;
import com.example.rbacdemo.model.user;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserService {
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    public user getUserByName(String username){
        return userMapper.getUserByName(username);
    }
}
