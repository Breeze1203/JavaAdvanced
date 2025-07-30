package org.pt.curator.distributelock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MultiLock
 * @Author pt
 * @Description
 * @Date 2025/7/30 22:07
 * 原子性：acquire 方法会尝试获取容器内所有的锁。只有当所有锁都成功获取时，acquire 才算成功。
 * 自动释放：如果在获取过程中有任何一个锁获取失败，它会自动释放所有已经成功获取的锁。
 * 统一管理：release 方法会释放容器内所有的锁
 **/

public class MultiLock{
    private static final String ZK_URL = "localhost:2181";
    private static final String LOCK_PATH_A = "/demo/multi-lock-a";
    private static final String LOCK_PATH_B = "/demo/multi-lock-b";

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3));
        client.start();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(1);

        System.out.println("--- 测试多重锁的原子性 ---");
        // 后台线程先占用锁B
        executor.submit(() -> {
            InterProcessMutex lockB = new InterProcessMutex(client, LOCK_PATH_B);
            try {
                lockB.acquire();
                System.out.println("后台线程: 已持有锁B。");
                latch.countDown(); // 通知主线程可以继续
                Thread.sleep(5000); // 持有5秒
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lockB.release();
                    System.out.println("后台线程: 已释放锁B。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        latch.await(); // 等待后台线程持有锁B

        InterProcessLock lockA = new InterProcessMutex(client, LOCK_PATH_A);
        InterProcessLock lockB = new InterProcessMutex(client, LOCK_PATH_B);
        InterProcessMultiLock multiLock = new InterProcessMultiLock(Arrays.asList(lockA, lockB));

        System.out.println("主线程: 尝试获取多重锁(A和B)...");
        try {
            if (!multiLock.acquire(2, TimeUnit.SECONDS)) {
                System.out.println("✅ 主线程: 获取多重锁失败，因为锁B被占用。符合预期！");
            }

            System.out.println("主线程: 等待后台线程释放锁B后，再次尝试...");
            Thread.sleep(4000); // 等待后台线程释放

            if (multiLock.acquire(2, TimeUnit.SECONDS)) {
                System.out.println("✅ 主线程: 成功获取多重锁！");
            } else {
                System.err.println("❌ 主线程: 获取多重锁失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (multiLock.isAcquiredInThisProcess()) {
                    multiLock.release();
                    System.out.println("⬇️ 主线程: 已释放多重锁。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        client.close();
        System.out.println("\n--- MultiLock 结束 ---");
    }
}