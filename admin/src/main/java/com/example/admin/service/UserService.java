package com.example.admin.service;

import com.example.admin.mapper.UserMapper;
import com.example.admin.model.User;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UserService")
public class UserService {
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }
    // 根据节点id查看是否有用户
    public List<User> getUserByOid(Integer id){
        return userMapper.getUserByOid(id);
    }

}
