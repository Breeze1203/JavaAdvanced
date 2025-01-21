package com.example.kafkademo.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @ClassName CustomProducerCallback
 * @Author pt
 * @Description kafka异步发送
 * @Date 2025/1/17 13:56
 **/
public class CustomProducerCallback {
    public static void main(String[] args) throws InterruptedException {
        // 1. 创建 kafka生产者的配置对象
        Properties properties = new Properties();
        // 2. 给kafka 配置对象添加配置信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
       // key,value 序列化（必须）：key.serializer，value.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        // 3. 创建 kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new
                KafkaProducer<String, String>(properties);
        // 4. 调用 send方法,发送消息
        for (int i = 100; i < 1000; i++) {
         // 添加回调
            kafkaProducer.send(new ProducerRecord<>("two",
                    "topic------demo " + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                      // 没有异常,输出信息到控制台
                        System.out.println(" 主 题 ： " +
                                metadata.topic() + "->" + "分区：" + metadata.partition());
                    } else {
                     // 出现异常打印
                        exception.printStackTrace();
                    }
                }
            });
            // 延迟一会会看到数据发往不同分区
            Thread.sleep(15);
        }
        kafkaProducer.close();
    }
}
