package com.example.admin.mapper;

import com.example.admin.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository(value = "UserMapper")
public interface UserMapper {

    // 根据用户名查找用户
    User getUserByName(@Param("username")String username);

    Integer updateUser(User u);

    List<User> getAllUser(User user);

    // 根据节点id,获取用户
    List<User> getUserByoId(@Param("organizationId")Integer id);

    Integer deleteById(@Param("id")Integer id);

    Integer addUser(User user);

    User getUserById(@Param("id") Integer id);

    User getUserByPhone(@Param("phone") Long phone);
}
