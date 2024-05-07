package com.example.springbootdynamic.service;

import com.example.springbootdynamic.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUser();
    public void addUser(User user);
}
