package org.example.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class DelayMsgQueue {
    private Jedis jedis;
    private String queue;

    public DelayMsgQueue(Jedis jedis, String queue) {
        this.jedis = jedis;
        this.queue = queue;
    }

    // 消息入队，data就是要发送的消息
    public void queue(Object data,int i){
        // 构建一个StudentMessage
        StudentMessage studentMessage = new StudentMessage();
        studentMessage.setData(data);
        studentMessage.setId(UUID.randomUUID().toString());
        // 序列化
        try {
            String s = new ObjectMapper().writeValueAsString(studentMessage);
            // 将元素添加到有序集合中 i加五秒，s(元素值)这里对应消息的内容
            jedis.zadd(queue,i,s);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 消息消费
    public void loop(){
        while (!Thread.interrupted()){
            // 获取指定区间的元素
            // 0 和 1 是可选参数，表示获取的元素的偏移量和数量。这里的 0 表示从第一个元素下标开始，1 表示只获取一个元素
            // 获取的也是一个集合
            Set<String> strings = jedis.zrangeByScore(queue, 0, 9, 0, 1);
            //Set<String> strings = jedis.zrangeByScore(queue, 0, System.currentTimeMillis());
            if(strings.isEmpty()){
                try {
                    // 如果消息是空的 休息500毫秒
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                // 跳过本次迭代，继续下一次
                continue;
            }
            // 如果读取到了消息，则直接读取消息处理 集合中获取迭代器的下一个元素，并将其存储在 next 变量中
            String next = strings.iterator().next();
            // 移除获取到的消息
            if(jedis.zrem(queue,next)>0){
                // 抢到了，接下来处理业务
                try {
                    StudentMessage studentMessage = new ObjectMapper().readValue(next, StudentMessage.class);
                    System.out.println("receive msg:"+new Date()+"..." +studentMessage);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
