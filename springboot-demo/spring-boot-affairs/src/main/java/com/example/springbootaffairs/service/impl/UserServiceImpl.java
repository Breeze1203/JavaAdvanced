package com.example.springbootaffairs.service.impl;

import com.example.springbootaffairs.dao.UserMapper;
import com.example.springbootaffairs.entity.User;
import com.example.springbootaffairs.service.UserService;
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
