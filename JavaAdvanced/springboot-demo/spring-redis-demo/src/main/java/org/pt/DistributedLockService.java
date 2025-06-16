package org.pt;

/**
 * @ClassName DistributedLockService
 * @Author pt
 * @Description
 * @Date 2025/6/16 15:53
 **/

import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service(value = "DistributedLockService")
public class DistributedLockService {

    private static final String LOCK_KEY_PREFIX = "lock:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final DefaultRedisScript<Long> LOCK_SCRIPT;
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;

    static {
        // 初始化加锁脚本
        LOCK_SCRIPT = new DefaultRedisScript<>();
        LOCK_SCRIPT.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/lock.lua")));
        LOCK_SCRIPT.setResultType(Long.class);

        // 初始化解锁脚本
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/unlock.lua")));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }



    /**
     * 尝试获取锁
     * @param resourceName 锁定的资源名称
     * @param lockValue 锁的持有者标识
     * @param expireTime 过期时间
     * @param unit 时间单位
     * @return true 如果成功获取锁, false otherwise
     */
    public boolean tryLock(String resourceName, String lockValue, long expireTime, TimeUnit unit) {
        String key = LOCK_KEY_PREFIX + resourceName;
        long expireMillis = unit.toMillis(expireTime);
        Long result = stringRedisTemplate.execute(
                LOCK_SCRIPT,
                Collections.singletonList(key),
                lockValue,
                String.valueOf(expireMillis)
        );

        return result != null && result == 1L;
    }

    /**
     * 释放锁
     * @param resourceName 锁定的资源名称
     * @param lockValue 锁的持有者标识 (必须与加锁时相同)
     */
    public void unlock(String resourceName, String lockValue) {
        String key = LOCK_KEY_PREFIX + resourceName;
        stringRedisTemplate.execute(UNLOCK_SCRIPT, Collections.singletonList(key), lockValue);
    }

    public void processOrder(String orderId) {
        String lockValue = UUID.randomUUID().toString();
        // 尝试获取订单锁，最长等待30秒
        if (tryLock("order:" + orderId, lockValue, 30, TimeUnit.SECONDS)) {
            try {
                System.out.println("成功获取锁，开始处理订单：" + orderId);
                Thread.sleep(500); // 模拟业务处理
                System.out.println("订单处理完成：" + orderId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // 确保释放锁
                unlock("order:" + orderId, lockValue);
                System.out.println("释放锁：" + orderId);
            }
        } else {
            System.out.println("获取锁失败，请稍后重试：" + orderId);
        }
    }
}
