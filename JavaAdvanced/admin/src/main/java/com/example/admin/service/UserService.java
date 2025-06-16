package com.example.admin.service;

import com.example.admin.mapper.UserMapper;
import com.example.admin.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UserService")
public class UserService implements UserMapper{
    @Resource(name = "UserMapper")
    private UserMapper userMapper;

    /*
    根据用户名查找用户信息
     */
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    /*
    查找所有用户
     */
    public List<User> getAllUser(User user) {
        return userMapper.getAllUser(user);
    }

    /*
   根据节点id查看是否有用户
    */
    @Override
    public List<User> getUserByoId(Integer id) {
        return userMapper.getUserByoId(id);
    }


    /*
    修改用户
     */
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /*
    根据用户id获取用户
     */
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }

    /*
    删除用户
     */
    public Integer deleteById(Integer id){
        return userMapper.deleteById(id);
    }

    /*
   添加用户
    */
    @Override
    public Integer addUser(User user) {
        return userMapper.addUser(user);
    }


    /*
    根据手机号查询用户
     */
    public User getUserByPhone(Long phone) {
        return userMapper.getUserByPhone(phone);
    }

    /*
    注销登录
     */
    @Override
    public List<User> getUserOutLogin(Integer id) {
        return userMapper.getUserOutLogin(id);
    }

    /*
    根据用户id获取用户信息
     */
    @Override
    public User getUser(Integer id) {
        return userMapper.getUser(id);
    }


}
