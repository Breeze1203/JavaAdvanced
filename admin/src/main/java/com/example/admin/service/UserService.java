package com.example.admin.service;

import com.example.admin.mapper.UserMapper;
import com.example.admin.model.User;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service(value = "UserService")
public class UserService {
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    public StatusUtil upUser(User u) {
        int update = userMapper.update(u);
        if (update != 0) {
            User user = userMapper.getUser(u.getId());
            return new StatusUtil("修改成功",200,user);
        }
        return new StatusUtil("网络异常，请稍后再试",300,null);
    }
}
