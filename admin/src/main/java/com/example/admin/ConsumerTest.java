package com.example.admin;

import com.alibaba.fastjson.JSON;
import com.example.admin.model.User;
import com.example.admin.util.MessageUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class ConsumerTest {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("message-consumer");
        consumer.setNamesrvAddr("192.168.3.81:9876");
        try {
            consumer.subscribe("short_message","*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for(MessageExt m:list){
                        byte[] body = m.getBody();
                        String s=new String(body);
                        User user = JSON.parseObject(s, User.class);
                        System.out.println(user);
                        try {
                            MessageUtil.sendMessage(user);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            consumer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }
}
