package com.example.admin.service;

import com.example.admin.mapper.MessageMapper;
import com.example.admin.model.Message;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "MessageService")
public class MessageService {
    @Resource(name = "MessageMapper")
    private MessageMapper messageMapper;

    public List<Message> getAllMessage(Integer id){
        return messageMapper.getMessage(id);
    }

    public Integer sendMessage(Message message) {
        return messageMapper.save(message);
    }

    public Integer upState(Integer id) {
        return messageMapper.upstate(id);
    }
}
