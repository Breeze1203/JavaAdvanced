package org.pt.kafka;

/**
 * @ClassName KafkaConsumerTest
 * @Author pt
 * @Description
 * @Date 2025/6/12 15:31
 **/
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerTest {

    public static void main(String[] args) {
        // 1. 配置消费者属性
        Properties props = new Properties();
        // 连接到 Kafka 集群
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        // 设置 Key 和 Value 的反序列化器。消费者将字节流转换回 String 对象。
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 设置消费者组 ID。组内所有消费者共同消费一个主题的数据。
        // 这是必须的配置。
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-java-test-group");
        // 设置当没有初始偏移量时，从何处开始消费。
        // "earliest" 会从主题的最早可用消息开始消费。
        // "latest" 会从最新的消息开始消费（即只消费消费者启动后产生的消息）。
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 2. 创建 KafkaConsumer 实例
        // 使用 try-with-resources 语句确保 consumer 在使用后被正确关闭
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {

            // 3. 订阅主题
            consumer.subscribe(Collections.singletonList("test-replication"));
            System.out.println("消费者已启动，正在等待消息...");
            // 4. 无限循环，持续从 Kafka 拉取消息
            // 在实际应用中，你可能需要一个更优雅的关闭机制
            while (true) {
                // poll() 方法是消费者的核心。它会去 Kafka 拉取数据。
                // 参数是超时时间，如果在这个时间内没有拉取到消息，它会返回一个空集合。
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                // 遍历拉取到的每一条记录
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("接收到消息: Key: %s, Value: %s, Partition: %d, Offset: %d%n",
                            record.key(), record.value(), record.partition(), record.offset());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
