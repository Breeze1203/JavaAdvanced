package com.example.springbootdynamic.dao;

import com.example.springbootdynamic.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "UserMapper")
public interface UserMapper {
    List<User> getAllUser();

    void addUser(User user);
}
