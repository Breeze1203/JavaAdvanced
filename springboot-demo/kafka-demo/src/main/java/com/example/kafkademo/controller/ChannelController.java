package com.example.kafkademo.controller;



import com.example.kafkademo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ChannelController
 * @Author pt
 * @Description
 * @Date 2025/1/16 13:55
 **/
@RestController
@RequestMapping("/user")
public class ChannelController {
    @PostMapping("/getUser")
    public User getUser(@RequestBody User user){
        return user;
    }
}
