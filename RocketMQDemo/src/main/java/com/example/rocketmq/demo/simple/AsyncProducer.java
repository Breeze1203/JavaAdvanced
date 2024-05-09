package com.example.rocketmq.demo.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                Message msg = new Message("TopicTest",
                        "TagA",
                        "OrderID188",
                        ("Hello world "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));

                //异步发送，SendCallback接收RocketMQ响应的结果，通过构建线程池的任务想RocketMQ发送消息
                producer.send(msg, new SendCallback() {

                    //成功
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        //使 CountDownLatch 计数值 减1
                        countDownLatch.countDown();
                            System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                        }
                    //失败
                        @Override
                        public void onException(Throwable e) {
                        //使 CountDownLatch 计数值 减 1
                            countDownLatch.countDown();
                            System.out.printf("%-10d Exception %s %n", index, e);
                            e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 主线程在阻塞，当计数器==0，就唤醒主线程往下执行。
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
