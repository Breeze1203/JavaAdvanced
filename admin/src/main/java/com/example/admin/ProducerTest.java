package com.example.admin;


import com.alibaba.fastjson.JSON;
import com.example.admin.model.User;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import java.nio.charset.StandardCharsets;

public class ProducerTest {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("message-producer");
        producer.setNamesrvAddr("192.168.3.81:9876");
        try {
            producer.start();
            Message message = new Message();
            message.setTopic("short_message");
            User user = new User();
            user.setPassword("eewd");
            user.setUsername("张三");
            user.setPhone(Long.valueOf("19972552055"));
            String jsonString = JSON.toJSONString(user);
            message.setBody(jsonString.getBytes(StandardCharsets.UTF_8));
            producer.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
