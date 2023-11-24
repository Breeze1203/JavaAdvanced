package com.example.admin;


import com.alibaba.fastjson.JSON;
import com.example.admin.model.User;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import java.nio.charset.StandardCharsets;

public class ProducerTest {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("code-producer");
        producer.setNamesrvAddr("192.168.3.84:9876");
        try {
            producer.start();
            Message message = new Message();
            message.setTopic("code_var");
            message.setBody("1234".getBytes(StandardCharsets.UTF_8));
            producer.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
