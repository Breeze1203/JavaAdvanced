package com.example.rocketmq.demo.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;


public class SyncDelProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
//        producer.createTopic();
//        producer.setMaxMessageSize();
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 1000; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            msg.setKeys("key-"+i);
            try{
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
                TimeUnit.SECONDS.sleep(1);

            }

        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }


}
