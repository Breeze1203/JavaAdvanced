package com.example.admin.config;

import com.alibaba.fastjson.JSON;
import com.example.admin.model.User;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

public class MessageProducer {
    public static void pushMessage(User user){
        DefaultMQProducer producer = new DefaultMQProducer("shortMessage-producer");
        producer.setNamesrvAddr("192.168.3.49:9876");
        try {
            producer.start();
            Message message = new Message();
            message.setTopic("short_message");
            String jsonString = JSON.toJSONString(user);
            message.setBody(jsonString.getBytes(StandardCharsets.UTF_8));
            producer.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
