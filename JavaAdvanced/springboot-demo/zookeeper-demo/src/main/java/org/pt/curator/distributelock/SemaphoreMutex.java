package org.pt.curator.distributelock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SemaphoreMutex
 * @Author pt
 * @Description
 * @Date 2025/7/30 22:10
 * 排他性：与 InterProcessMutex 相同。
 * 不可重入：同一个线程在持有锁之后，如果再次尝试获取，将会被永久阻塞。
 * 不记录持有者：任何线程都可以释放这个锁（尽管这通常不是推荐的做法）。它的内部实现是基于只有一个许可的信号量
 **/

public class SemaphoreMutex{
    private static final String ZK_URL = "localhost:2181";
    private static final String LOCK_PATH = "/demo/semaphore-mutex-lock";

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3));
        client.start();
        InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(client, LOCK_PATH);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.println("--- 测试 InterProcessSemaphoreMutex ---");

        try {
            // 1. 第一次尝试获取锁
            System.out.println("主线程: 正在尝试获取锁...");
            if (lock.acquire(10, TimeUnit.SECONDS)) {
                System.out.println("✅ 主线程: 成功获取锁！");

                // 2. 关键测试：在持有锁的情况下，再次尝试获取（测试不可重入性）
                System.out.println("\n主线程: 再次尝试获取同一个锁（测试不可重入性）...");
                if (!lock.acquire(2, TimeUnit.SECONDS)) {
                    System.out.println("✅ 主线程: 第二次获取锁失败，线程被阻塞后超时。这证明了锁是【不可重入】的，符合预期！");
                } else {
                    System.err.println("❌ 主线程: 不应该能再次获取锁！");
                    lock.release(); // 如果意外获取，也释放掉
                }

                // 3. 测试排他性：当主线程持有锁时，其他线程无法获取
                System.out.println("\n主线程: 现在持有锁，让后台线程尝试获取...");
                executor.submit(() -> {
                    InterProcessSemaphoreMutex lock2 = new InterProcessSemaphoreMutex(client, LOCK_PATH);
                    try {
                        System.out.println("后台线程: 正在尝试获取锁...");
                        if (!lock2.acquire(2, TimeUnit.SECONDS)) {
                            System.out.println("✅ 后台线程: 获取锁失败。这证明了锁是【排他】的，符合预期！");
                        } else {
                            System.err.println("❌ 后台线程: 不应该能获取到锁！");
                            lock2.release();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                // 等待后台线程执行完毕
                Thread.sleep(3000);

            } else {
                System.err.println("❌ 主线程: 第一次获取锁失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (lock.isAcquiredInThisProcess()) {
                    lock.release();
                    System.out.println("\n⬇️ 主线程: 已释放锁。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        client.close();
        System.out.println("\n--- SemaphoreMutexDemo 结束 ---");
    }
}
