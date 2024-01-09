package com.example.admin.mapper;

import com.example.admin.model.UserMessage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "UserMessageMapper")
public interface UserMessageMapper {
    /*
    获取用户消息列表
     */
    UserMessage getUserMessage(@Param("id")Integer id);

    /*
    插入用户消息
     */
    Integer saveUserMessage(UserMessage userMessage);

    /*
    更改所有者消息
     */
    Integer upUserMessage(UserMessage userMessage);
}
