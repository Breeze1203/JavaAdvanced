package com.example.admin.controller;

import com.example.admin.model.Message;
import com.example.admin.service.MessageService;
import com.example.admin.util.DateUtil;
import com.example.admin.util.StatusMessage;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    @Resource(name = "MessageService")
    private MessageService messageService;



    @GetMapping("/messageInit")
    public List<Message> getMessage(@RequestParam("mid")Integer id){
        return messageService.getAllMessage(id);
    }

    @PostMapping("/sendMessage")
    public StatusUtil saveMessage(@RequestParam("sid")Integer sid,@RequestParam("rid")Integer rid,@RequestParam("content")String content){
        Message message = new Message();
        message.setContent(content);
        message.setState(false);
        message.setSend_id(sid);
        message.setReceive_id(rid);
        message.setTime(DateUtil.format(new Date()));
        Integer i = messageService.sendMessage(message);
        if(i>0){
            return new StatusUtil(StatusMessage.SEND_SUCCESS.getMessage(), 200,null);
        }else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500,null);
        }
    }

    @GetMapping("/updateState")
    public StatusUtil upState(@RequestParam("id")Integer id){
        Integer i = messageService.upState(id);
        if(i>0){
            return new StatusUtil(StatusMessage.UPDATE_SUCCESS.getMessage(), 200,null);
        }else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500,null);
        }
    }
    @PostMapping("/deleteMessage")
    public StatusUtil deleteMessage(@RequestParam("id")Integer id,@RequestParam("mId")Integer mId){
        Integer i = messageService.deleteMessage(id, mId);
        if(i>0){
            return new StatusUtil(StatusMessage.DELETE_SUCCESS.getMessage(), 200,null);
        }else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(),500,null);
        }
    }
}
