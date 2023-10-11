package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class LockTest {
    /*
    分布式锁限制程序的并发执行 就是进来一个线程先占位，当别的线程进来操作时
    发现已经有人占位则会放弃或稍后再试
    SETNX 命令用于在 Redis 中设置一个键值对，当且仅当该键不存在时才进行设置。
    如果键已经存在，则 SETNX 命令不会执行任何操作
     */
    public static void main(String[] args) {
        Redis redis = new Redis();
        redis.execute(jedis -> {
            // 还有一种设置键值对时同时设置过期时间 成功返回ok
            String set = jedis.set("k1", "v1", new SetParams().nx().ex(5));
            if (set != null && "OK".equals(set)) {
                jedis.setnx("java", "pt");
                String s = jedis.get("java");
                System.out.println(s);
                jedis.del("k1");
            }
            /**
             Long setnx = jedis.setnx("k1", "v1");
             if(setnx==1){
             // 给锁设置过期时间，避免锁无法的到释放
             jedis.expire("k1",5);
             // 没人占位
             jedis.setnx("javaboy", "pt");
             String s = jedis.get("javaboy");
             System.out.println(s);
             jedis.del("k1");
             }else {
             // 有人占位
             }
             */
        });
    }
}
