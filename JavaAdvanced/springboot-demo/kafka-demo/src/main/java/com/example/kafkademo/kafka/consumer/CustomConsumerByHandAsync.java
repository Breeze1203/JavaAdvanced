package com.example.kafkademo.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @ClassName CustomConsumerByHandAsync
 * @Author pt
 * @Description
 * @Date 2025/1/20 15:37
 **/
public class CustomConsumerByHandAsync {
    public static void main(String[] args) {
        // 1. 创建 kafka消费者配置类
        Properties properties = new Properties();
// 2. 添加配置参数
        // 添加连接
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
// 配置序列化 必须
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
// 配置消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
// 是否自动提交 offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                "false");
//3. 创建Kafka消费者
        KafkaConsumer<String, String> consumer = new
                KafkaConsumer<>(properties);
//4. 设置消费主题 形参是列表
        consumer.subscribe(Arrays.asList("my_topic"));
//5. 消费数据
        while (true){
// 读取消息
            ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofSeconds(1));
// 输出消息
            for (ConsumerRecord<String, String> consumerRecord :
                    consumerRecords) {
                System.out.println(consumerRecord.value());
            }
// 异步提交offset
            consumer.commitAsync();
        }
    }
}
