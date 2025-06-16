package org.pt.kafka;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

/**
 * @ClassName KafkaProducerTest
 * @Author pt
 * @Description
 * @Date 2025/6/12 15:30
 **/

public class KafkaProducerTest {

    public static void main(String[] args) {
        // 配置生产者属性
        Properties props = new Properties();
        // 连接到 Kafka 集群。
        // 为了高可用，最好提供多个 Broker 的地址。
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        // 设置 Key 和 Value 的序列化器。生产者将 String 类型的 Key 和 Value 转换成字节流发送。
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // acks=all 保证了消息在被写入 Leader 和所有同步副本后才被认为是成功的。
        // 这是最安全但延迟最高的设置，非常适合测试副本机制。
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        // 2. 创建 KafkaProducer 实例
        // 使用 try-with-resources 语句确保 producer 在使用后被正确关闭
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            System.out.println("开始发送消息...");

            for (int i = 0; i < 10; i++) {
                String topic = "test-replication";
                String key = "key-" + i;
                String value = "Hello from Java Producer, message #" + i;

                // 创建一条要发送的记录
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

                // 3. 异步发送消息，并设置回调函数
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (exception == null) {
                            // 消息发送成功
                            System.out.println("发送成功: " +
                                    "Topic: " + metadata.topic() + ", " +
                                    "Partition: " + metadata.partition() + ", " +
                                    "Offset: " + metadata.offset() + ", " +
                                    "Timestamp: " + metadata.timestamp());
                        } else {
                            // 消息发送失败
                            System.err.println("发送失败，错误: " + exception.getMessage());
                        }
                    }
                });
            }

            // 4. 强制将缓冲区中的所有消息立即发送出去
            producer.flush();
            System.out.println("所有消息已发送完毕。");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
