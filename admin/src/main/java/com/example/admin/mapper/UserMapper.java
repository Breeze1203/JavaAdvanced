package com.example.admin.mapper;

import com.example.admin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository(value = "UserMapper")
public interface UserMapper {

    // 根据用户名查找用户
    User getUserByName(@Param("username")String username);

    int update(User u);

    User getUser(@Param("id") Integer id);
}
