package com.example.adminflow.mapper;

import com.example.adminflow.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository("UserMapper")
public interface UserMapper {

   // 根据用户名查找用户
    User getUserByName(@Param("username") String username);

    int update(User u);

    User getUser(@Param("id") Integer id);
}
