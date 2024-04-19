package com.example.springbootaffairs.dao;

import com.example.springbootaffairs.entity.Student;
import com.example.springbootaffairs.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "UserMapper")
public interface UserMapper {
    List<User> getAllUser();

    void addUser(User user);
}
