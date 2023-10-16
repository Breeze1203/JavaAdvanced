package com.example.rbacdemo.mapper;

import com.example.rbacdemo.model.user;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository("UserMapper")
public interface UserMapper {

   // 根据用户名查找用户
    user getUserByName(@Param("username") String username);
}
