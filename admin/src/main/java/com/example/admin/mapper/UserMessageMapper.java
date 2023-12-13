package com.example.admin.mapper;

import com.example.admin.model.UserMessage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "UserMessageMapper")
public interface UserMessageMapper {
    UserMessage getUserMessage(@Param("id")Integer id);

    Integer saveUserMessage(UserMessage userMessage);

    // 更改所有者消息
    Integer upUserMessage(UserMessage userMessage);
}
