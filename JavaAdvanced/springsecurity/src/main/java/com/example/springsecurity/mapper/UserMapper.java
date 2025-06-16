package com.example.springsecurity.mapper;

import com.example.springsecurity.model.User;
import org.springframework.stereotype.Repository;

@Repository(value = "UserMapper")
public interface UserMapper {
    User getUserByUserName(String userName);
}
