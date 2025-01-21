package com.example.kafkademo.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @ClassName CustomProducerCallbackPartitions
 * @Author pt
 * @Description
 * @Date 2025/1/17 14:30
 **/
public class CustomProducerCallbackPartitions {
    public static void main(String[] args) {
        // 1. 创建 kafka生产者的配置对象
        Properties properties = new Properties();
        // 2. 给kafka 配置对象添加配置信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        // 设置acks
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        // 重试次数retries，默认是int最大值，2147483647
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 添加自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.example.kafkademo.kafka.producer.MyPartitioner");
       // key,value 序列化（必须）：key.serializer，value.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        // batch.size：批次大小，默认16K
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024*1024*10);
        // linger.ms：等待时间，默认0
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 100000);
        // compression.type：压缩，默认 none，可配置值 gzip、snappy、lz4和 zstd
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");
        // RecordAccumulator：缓冲区大小，默认32M：buffer.memory
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);
        KafkaProducer<String, String> kafkaProducer = new
                KafkaProducer<>(properties);
        for (int i = 300; i < 301; i++) {
            // 指定数据发送到 1 号分区，key 为空（IDEA 中 ctrl + p 查看参数）
            kafkaProducer.send(new ProducerRecord<>("my_topic",2,"","pengtao kafka生产者----> " + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata,
                                         Exception e) {
                    if (e == null){
                        System.out.println(" 主 题 ： " +
                                metadata.topic() + "->" + "分区：" + metadata.partition()
                        );
                    }else {
                        e.printStackTrace();
                    }
                }
            });
        }
        kafkaProducer.close();
    }
}
