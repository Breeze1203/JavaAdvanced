package org.pt.curator.distributelock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ReadWriteLock
 * @Author pt
 * @Description
 * @Date 2025/7/30 22:01
 * 读锁共享：多个进程可以同时持有读锁，只要没有进程持有写锁。
 * 写锁排他：当一个进程持有写锁时，其他任何进程都不能获取读锁或写锁。
 * 适用场景：适用于“读多写少”的场景，可以极大地提升并发性能。例如，一个很少被修改，但会被频繁读取的系统配置
 **/
public class ReadWriteLock {
    private static final String ZK_URL = "localhost:2181";
    private static final String LOCK_PATH = "/demo/rw-lock";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3));
        client.start();
        InterProcessReadWriteLock rwLock = new InterProcessReadWriteLock(client, LOCK_PATH);
        InterProcessLock readLock = rwLock.readLock();
        InterProcessLock writeLock = rwLock.writeLock();
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("--- 测试写锁的排他性 ---");
        System.out.println("主线程: 正在获取写锁...");
        writeLock.acquire();
        System.out.println("✅ 主线程: 已获取写锁。");
        // 尝试获取读锁（应该失败）
        executor.submit(() -> {
            try {
                System.out.println("线程Read-1: 尝试获取读锁...");
                if (!readLock.acquire(2, TimeUnit.SECONDS)) {
                    System.out.println("✅ 线程Read-1: 获取读锁失败。符合预期！");
                }
            } catch (Exception e) { e.printStackTrace(); }
        });
        Thread.sleep(500);

        // 尝试获取写锁（应该失败）
        executor.submit(() -> {
            try {
                System.out.println("线程Write-1: 尝试获取写锁...");
                if (!writeLock.acquire(2, TimeUnit.SECONDS)) {
                    System.out.println("✅ 线程Write-1: 获取写锁失败。符合预期！");
                }
            } catch (Exception e) { e.printStackTrace(); }
        });

        Thread.sleep(3000);
        writeLock.release();
        System.out.println("⬇️ 主线程: 已释放写锁。");


        System.out.println("\n--- 测试读锁的共享性 ---");
        System.out.println("主线程: 正在获取读锁...");
        readLock.acquire();
        System.out.println("✅ 主线程: 已获取读锁。");

        // 尝试获取另一个读锁（应该成功）
        executor.submit(() -> {
            try {
                System.out.println("线程Read-2: 尝试获取读锁...");
                if (readLock.acquire(2, TimeUnit.SECONDS)) {
                    System.out.println("✅ 线程Read-2: 成功获取读锁。符合预期！");
                    readLock.release();
                    System.out.println("⬇️ 线程Read-2: 已释放读锁。");
                }
            } catch (Exception e) { e.printStackTrace(); }
        });
        Thread.sleep(500);

        // 尝试获取写锁（应该失败）
        executor.submit(() -> {
            try {
                System.out.println("线程Write-2: 尝试获取写锁...");
                if (!writeLock.acquire(2, TimeUnit.SECONDS)) {
                    System.out.println("✅ 线程Write-2: 获取写锁失败。符合预期！");
                }
            } catch (Exception e) { e.printStackTrace(); }
        });

        Thread.sleep(3000);
        readLock.release();
        System.out.println("⬇️ 主线程: 已释放读锁。");
        executor.shutdown();
        client.close();
        System.out.println("\n--- ReadWriteLock结束 ---");
    }
}
