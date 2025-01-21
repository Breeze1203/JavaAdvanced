package com.example.kafkademo.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @ClassName ProducerTransactions
 * @Author pt
 * @Description
 * @Date 2025/1/20 14:07
 **/
/*
// 1 初始化事务
void initTransactions();
// 2 开启事务
void beginTransaction() throws ProducerFencedException;
// 3 在事务内提交已经消费的偏移量（主要用于消费者）
void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets,
String consumerGroupId) throws
ProducerFencedException;
// 4 提交事务
void commitTransaction() throws ProducerFencedException;
// 5 放弃事务（类似于回滚事务的操作）
void abortTransaction() throws ProducerFencedException;
 */
public class ProducerTransactions {
    public static void main(String[] args) {
        // 1. 创建 kafka生产者的配置对象
        Properties properties = new Properties();
       // 2. 给kafka 配置对象添加配置信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
       // key,value 序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        // 设置事务id（必须），事务id任意起名
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,
                "transaction_id_0");
        // 3. 创建 kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new
                KafkaProducer<String, String>(properties);
       // 初始化事务
        kafkaProducer.initTransactions();
        // 开启事务
        kafkaProducer.beginTransaction();
        try {
        // 4. 调用send 方法,发送消息
            for (int i = 0; i < 5; i++) {
        // 发送消息
                kafkaProducer.send(new ProducerRecord<>("my_topic",2,"",
                        "atguigu " + i));
            }
           // 提交事务
            kafkaProducer.commitTransaction();
        } catch (Exception e) {
           // 终止事务(相当于回滚)
            kafkaProducer.abortTransaction();
        } finally {
           // 5. 关闭资源
            kafkaProducer.close();
        }
    }
}
