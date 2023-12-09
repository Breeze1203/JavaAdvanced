package com.example.admin.mapper;

import com.example.admin.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "MessageMapper")
public interface MessageMapper {
    List<Message> getMessage(@Param("id") Integer id);

    Integer save(Message message);
    // 修改消息状态
    Integer upstate(@Param("id")Integer id);
}
