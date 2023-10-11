package org.example;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

public class Redis{
    private JedisPool jedisPool;

    public Redis() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        // 连接池最大空闲数
        config.setMaxIdle(300);
        // 连接池最大连接数
        config.setMaxTotal(1000);
        // 连接池最大等待时间如果时-1则表示没有限制
        config.setMaxWaitMillis(30000);
        // 在空闲时检查有效性
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config,"192.168.3.64",6388,30000,"pt2001") ;
    }


    public void execute(CallWithJedis callWithJedis) {
        callWithJedis.call(jedisPool.getResource());
    }
}
