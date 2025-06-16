package com.example.kafkademo.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName CustomProducerSync
 * @Author pt
 * @Description
 * @Date 2025/1/17 14:23
 **/
public class CustomProducerSync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 创建 kafka生产者的配置对象
        Properties properties = new Properties();
        // 2. 给kafka 配置对象添加配置信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // key,value 序列化（必须）：key.serializer，value.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        // 3. 创建 kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new
                KafkaProducer<String, String>(properties);
        // 4. 调用 send方法,发送消息
        for (int i = 0; i < 10; i++) {
            // 异步发送 默认
            kafkaProducer.send(new
                    ProducerRecord<>("three", "异步发送----kafka" + i));
           // 同步发送
            kafkaProducer.send(new
                    ProducerRecord<>("three", "同步发送----kafka" + i)).get();
        }
       // 5. 关闭资源
        kafkaProducer.close();
    }
}
