package com.example.springbootaffairs.service;

import com.example.springbootaffairs.entity.Student;
import com.example.springbootaffairs.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUser();
    public void addUser(User user);
}
