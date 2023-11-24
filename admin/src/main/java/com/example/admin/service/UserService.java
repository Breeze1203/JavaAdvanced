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

    public List<User> getAllUser(User user) {
        return userMapper.getAllUser(user);
    }
    // 根据节点id查看是否有用户
    public List<User> getUserByOid(Integer id){
        return userMapper.getUserByoId(id);
    }

    // 修改用户
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    // 根据用户id获取用户
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }

    // 删除用户
    public Integer deleteById(Integer id){
        return userMapper.deleteById(id);
    }

    // 添加用户
    public Integer insertUser(User user) {
        return userMapper.addUser(user);
    }

    // 根据手机号查询用户
    public User getUserByPhone(Long phone) {
        return userMapper.getUserByPhone(phone);
    }
}
