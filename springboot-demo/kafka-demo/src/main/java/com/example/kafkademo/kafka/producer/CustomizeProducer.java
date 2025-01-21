package com.example.kafkademo.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @ClassName CustomizeProducer
 * @Author pt
 * @Description
 * @Date 2025/1/17 13:48
 **/
public class CustomizeProducer {
    public static void main(String[] args) {
        // 1. 创建 kafka生产者的配置对象
        Properties properties = new Properties();
        // 2. 给kafka 配置对象添加配置信息：bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
       // 3. 创建 kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new
                KafkaProducer<String, String>(properties);
      // 4. 调用 send方法,发送消息
        for (int i = 200; i < 210; i++) {
            kafkaProducer.send(new
                    ProducerRecord<>("my_topic",2,"","分区二上传数据:----atguigu_pt " + i));
        }
      // 5. 关闭资源
        kafkaProducer.close();
    }
}
