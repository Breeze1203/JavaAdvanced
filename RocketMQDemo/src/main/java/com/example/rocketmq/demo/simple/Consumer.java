package com.example.rocketmq.demo.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;

import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户端： 消费消息
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        //构建消费者实例，相同的组名的多个消费者实例，搭建消费集群
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        //指定namerserver地址，不是broker，多个Namerser，而不是Broker
        consumer.setNamesrvAddr("localhost:9876;localhost:98762");
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //指定topic进行消息的订阅，全部 *
        consumer.subscribe("TopicTest", "*");
//        consumer.setMaxReconsumeTimes(18);
//        consumer.getConsumeThreadMin();
        consumer.getConsumeThreadMin();
        consumer.getConsumeThreadMax();

        //注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            //第一个参数， 消息的集合。第一个参数 消费的上下文。
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,

                                                            ConsumeConcurrentlyContext context) {
                //接收的RocketMQ发送的消息
                for (MessageExt msg:msgs){

                    //消费消息，是多线程实现  ！
                    System.out.printf("%s Receive New Messages: %s %n",
                            Thread.currentThread().getName(), new String(msg.getBody()));


                   String key =  msg.getKeys();



                }


//                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //消费启动
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
