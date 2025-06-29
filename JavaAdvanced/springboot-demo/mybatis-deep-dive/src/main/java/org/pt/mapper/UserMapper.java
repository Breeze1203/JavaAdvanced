package org.pt.mapper;

import org.pt.entity.User;
/**
 * @ClassName UserMapper
 * @Author pt
 * @Description
 * @Date 2025/6/28 21:24
 **/

public interface UserMapper {
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    User selectUser(int id);

    /**
     * 插入一个新用户
     * @param user 用户对象
     */
    void insertUser(User user);
}