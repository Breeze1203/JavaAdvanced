package org.pt.kafka;

/**
 * @ClassName MultiThreadConsumerTest
 * @Author pt
 * @Description
 * @Date 2025/6/12 15:52
 **/

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadConsumerTest {

    public static void main(String[] args) {
        // --- 配置信息 ---
        final String bootstrapServers = "localhost:9092,localhost:9093,localhost:9094";
        final String groupId = "my-multi-threaded-group";
        final String topic = "test-replication";
        final int numConsumers = 3;

        // 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

        // 创建并提交消费者任务
        for (int i = 0; i < numConsumers; i++) {
            ConsumerTask consumerTask = new ConsumerTask(bootstrapServers, groupId, topic, "Consumer-" + (i + 1));
            executor.submit(consumerTask);
        }

        // 添加一个关闭钩子，用于优雅地关闭消费者
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("捕获到关闭信号，正在关闭 ExecutorService...");
            executor.shutdown(); // 禁止提交新任务
            try {
                // 等待现有任务结束
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("ExecutorService 未能在60秒内关闭，强制关闭。");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
            System.out.println("ExecutorService 已关闭。");
        }));
    }

    /**
     * ConsumerTask 封装了每个消费者线程的逻辑
     */
    static class ConsumerTask implements Runnable {
        private final KafkaConsumer<String, String> consumer;
        private final String topic;
        private final String consumerId;

        public ConsumerTask(String bootstrapServers, String groupId, String topic, String consumerId) {
            this.topic = topic;
            this.consumerId = consumerId;

            // 为每个线程创建独立的消费者配置
            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

            // 创建该线程专属的 KafkaConsumer 实例
            this.consumer = new KafkaConsumer<>(props);
        }

        @Override
        public void run() {
            try {
                // 订阅主题
                consumer.subscribe(Collections.singletonList(topic));
                System.out.printf("[%s] 已启动并订阅主题 '%s'%n", consumerId, topic);
                // 无限循环拉取消息
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(Long.MAX_VALUE));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf("[%s] 接收到消息: Key: %s, Value: %s, Partition: %d, Offset: %d%n",
                                consumerId, record.key(), record.value(), record.partition(), record.offset());
                    }
                    try {
                        consumer.commitSync();
                        System.out.printf("[%s] 偏移量已成功提交。%n", consumerId);
                    } catch (Exception e) {
                        System.err.printf("[%s] 提交偏移量失败: %s%n", consumerId, e.getMessage());
                        // 这里可以加入重试逻辑或错误处理
                    }
                }
            } catch (WakeupException e) {
                // 这是捕获到的优雅关闭信号，不是错误
                System.out.printf("[%s] 捕获到 WakeupException，准备关闭...%n", consumerId);
            } catch (Exception e) {
                System.err.printf("[%s] 发生未知错误: %s%n", consumerId, e.getMessage());
                e.printStackTrace();
            } finally {
                // 关闭消费者，这会提交最后的偏移量并触发再均衡
                consumer.close();
                System.out.printf("[%s] 已关闭。%n", consumerId);
            }
        }
    }
}
