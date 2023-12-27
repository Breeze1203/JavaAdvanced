package com.example.mybatisplus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.User;



public interface UserDao extends BaseMapper<User> {
    User getUserById();
}
