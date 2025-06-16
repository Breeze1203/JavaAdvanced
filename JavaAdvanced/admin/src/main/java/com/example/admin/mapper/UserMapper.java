package com.example.admin.mapper;

import com.example.admin.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository(value = "UserMapper")
public interface UserMapper {

    /*
    根据用户名查找用户
     */
    User getUserByName(@Param("username")String username);

    /*
    修改用户
     */
    Integer updateUser(User u);

    /*
    获取所有用户
     */
    List<User> getAllUser(User user);

    /*
    根据节点id,获取用户
     */
    List<User> getUserByoId(@Param("organizationId")Integer id);

    /*
    根据id删除用户
     */
    Integer deleteById(@Param("id")Integer id);

    /*
    添加用户
     */
    Integer addUser(User user);

    /*
    根据id获取用户
     */
    User getUserById(@Param("id") Integer id);

    /*
    根据手机号搜索用户
     */
    User getUserByPhone(@Param("phone") Long phone);

    /*
    获取当前登录用户除外的所有用户，用于私信功能
     */
    List<User> getUserOutLogin(@Param("id") Integer id);

    /*
    根据id查询用户
     */
    User getUser(@Param("id")Integer id);

}
