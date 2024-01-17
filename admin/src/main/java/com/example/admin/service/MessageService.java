package com.example.admin.service;

import com.example.admin.mapper.MessageMapper;
import com.example.admin.mapper.UserMessageMapper;
import com.example.admin.model.Message;
import com.example.admin.model.UserMessage;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "MessageService")
public class MessageService implements MessageMapper{
    @Resource(name = "MessageMapper")
    private MessageMapper messageMapper;

    @Resource(name = "UserMessageMapper")
    private UserMessageMapper userMessageMapper;

    /*
    获取所有消息列表
     */
    public List<Message> getAllMessage(Integer id){
        UserMessage userMessage = userMessageMapper.getUserMessage(id);
        String mId = userMessage.getMId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        if(mId==null){
            map.put("messageIds",null);
        }else {
            String[] split = mId.split(",");
            map.put("messageIds",split);
        }
        return getMessage(map);
    }

    /*
    发送消息
     */
    public Integer sendMessage(Message message) {
        UserMessage user = userMessageMapper.getUserMessage(message.getSend_id());
        if(user==null){
           userMessageMapper.saveUserMessage(new UserMessage(null,message.getSend_id(),null));
        }
        return save(message);
    }

    /*
    更改消息状态
     */
    public Integer upState(Integer id) {
        return upstate(id);
    }

    /*
     删除消息 第一个参数是消息所属用户id，第二个是要删除消息的id
     */
    public Integer deleteMessage(Integer id,Integer mId){
        UserMessage user = userMessageMapper.getUserMessage(id);
        if(user.getMId()==null){
            user.setMId(String.valueOf(mId));
        }else {
            user.setMId(user.getMId()+","+mId);
        }
        return userMessageMapper.upUserMessage(user);
    }

    @Override
    public List<Message> getMessage(Map<String, Object> paramMap) {
        return messageMapper.getMessage(paramMap);
    }

    @Override
    public Integer save(Message message) {
        return messageMapper.save(message);
    }

    @Override
    public Integer upstate(Integer id) {
        return messageMapper.upstate(id);
    }
}
