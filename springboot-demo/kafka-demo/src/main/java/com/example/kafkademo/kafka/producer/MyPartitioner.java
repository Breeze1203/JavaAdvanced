package com.example.kafkademo.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @ClassName MyPartitioner
 * @Author pt
 * @Description 自定义分区器
 * @Date 2025/1/20 13:43
 **/
public class MyPartitioner implements Partitioner {
    /*
     * 返回信息对应的分区
     * @param topic 主题
     * @param key 消息的key
     * @param keyBytes 消息的key序列化后的字节数组
     * @param value 消息的value
     * @param valueBytes 消息的value序列化后的字节数组
     * @param cluster 集群元数据可以查看分区信息
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[]
            keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 获取消息
        String msgValue = value.toString();
       // 创建partition
        int partition;
      // 判断消息是否包含 atguigu
        if (msgValue.contains("pengtao")) {
            partition = 2;
        } else {
            partition = 1;
        }
        // 返回分区号
        return partition;
    }

    // 关闭资源
    @Override
    public void close() {
    }

    // 配置方法
    @Override
    public void configure(Map<String, ?> configs) {
    }
}
