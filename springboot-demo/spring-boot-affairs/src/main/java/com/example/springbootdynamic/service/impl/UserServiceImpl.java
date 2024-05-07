package com.example.springbootdynamic.service.impl;

import com.example.springbootdynamic.dao.UserMapper;
import com.example.springbootdynamic.entity.User;
import com.example.springbootdynamic.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }
}
