package com.example.rocketmq.demo.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;


/**
 * 生产者 同步发送
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        //构建生产者示例，生产者的组名
        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");

        //指定nameserver地址
        producer.setNamesrvAddr("47.113.114.112:9876");

        //启动生产者示例
        producer.start();
        //
        for (int i = 0; i < 10; i++) {
            //构建消息的实例，topic 一级分类，tag 二级分类，body 消息体 ，消息的内容
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,

                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /*消息内容 */
            );
            msg.setKeys("key-"+i); //key ，链接追踪
            //发送消息，同步发送，会接收响应的结果
            SendResult sendResult = producer.send(msg);

            System.out.printf("%s%n", sendResult);
        }
        //关闭生产者实例
        producer.shutdown();
    }


}
