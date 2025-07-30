package org.pt.curator.distributelock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ReentrantExclusiveLock
 * @Author pt
 * @Description
 * @Date 2025/7/30 21:46
 * 可重入排他锁
 * 排他性：在任何时刻，只有一个客户端（线程）能够持有锁。
 * 可重入 (Re-entrant)：同一个线程可以多次成功获取（acquire）同一个锁，而不会自己把自己锁死。每次获取都必须对应一次释放（release）
 * 锁的持有者：Curator 会记录持有锁的线程，只有持有锁的线程才能释放它
 **/
public class ReentrantExclusiveLock {

        private static final String ZK_URL = "localhost:2181";
        private static final String LOCK_PATH = "/demo/mutex-lock";

        public static void main(String[] args) throws InterruptedException {
            ExecutorService executor = Executors.newFixedThreadPool(2);

            // 创建两个客户端，模拟两个不同的进程
            CuratorFramework client1 = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3));
            CuratorFramework client2 = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3));
            client1.start();
            client2.start();

            System.out.println("客户端1和客户端2已启动...");

            // 线程1尝试获取锁
            executor.submit(() -> {
                InterProcessMutex lock = new InterProcessMutex(client1, LOCK_PATH);
                try {
                    System.out.println("线程1: 正在尝试获取锁...");
                    if (lock.acquire(10, TimeUnit.SECONDS)) {
                        System.out.println("✅ 线程1: 成功获取锁！");
                        // 测试可重入性
                        System.out.println("线程1: 再次尝试获取锁（测试可重入）...");
                        if (lock.acquire(1, TimeUnit.SECONDS)) {
                            System.out.println("✅ 线程1: 再次获取锁成功！");
                            lock.release(); // 释放第二次获取
                            System.out.println("线程1: 释放第二次获取的锁。");
                        }

                        System.out.println("线程1: 执行业务逻辑中，将持有锁5秒...");
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (lock.isAcquiredInThisProcess()) {
                            lock.release();
                            System.out.println("⬇️ 线程1: 释放锁。");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // 稍微等待，确保线程1先启动
            Thread.sleep(1000);
            // 线程2尝试获取锁
            executor.submit(() -> {
                InterProcessMutex lock = new InterProcessMutex(client2, LOCK_PATH);
                try {
                    System.out.println("线程2: 正在尝试获取锁...");
                    if (lock.acquire(2, TimeUnit.SECONDS)) {
                        System.err.println("❌ 线程2: 不应该能获取到锁！");
                        lock.release();
                    } else {
                        System.out.println("✅ 线程2: 获取锁失败，因为线程1正持有锁。符合预期！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executor.shutdown();
            executor.awaitTermination(20, TimeUnit.SECONDS);
            client1.close();
            client2.close();
            System.out.println("\n--- ReentrantExclusiveLock ---");
        }
}
