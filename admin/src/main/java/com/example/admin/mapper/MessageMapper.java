package com.example.admin.mapper;

import com.example.admin.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(value = "MessageMapper")
public interface MessageMapper {
    List<Message> getMessage(Map<String, Object> paramMap);

    Integer save(Message message);
    // 修改消息状态
    Integer upstate(@Param("id") Integer id);
}
