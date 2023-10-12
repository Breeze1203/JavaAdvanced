package org.example.message;

import org.example.lock.Redis;

public class DelayTest {
    public static void main(String[] args) {
        Redis redis = new Redis();
        redis.execute(jedis -> {
            DelayMsgQueue delayMsgQueue = new DelayMsgQueue(jedis, "message-queue");
            // 构建生产者
            Thread producer=new Thread(){
                @Override
                public void run(){
                    for(int i=0;i<5;i++){
                        delayMsgQueue.queue("消息"+i,i);
                    }
                }
            };
            Thread consumer=new Thread(){
                @Override
                public void run(){
                        delayMsgQueue.loop();
                }
            };
            // 启动
            producer.start();
            consumer.start();
            //休息7秒钟，停止程序
            try {
                Thread.sleep(10000);
                consumer.interrupt();
                producer.stop();
                consumer.stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
