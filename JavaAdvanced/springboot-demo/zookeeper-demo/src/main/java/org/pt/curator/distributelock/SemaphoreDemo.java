package org.pt.curator.distributelock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName SemaphoreDemo
 * @Author pt
 * @Description
 * @Date 2025/7/30 22:03
 * 有限许可：在创建时，可以指定“许可”的数量。
 * 并发控制：允许多个进程同时访问一个资源，但总数不能超过设定的许可数。
 * 公平性：通常是公平的，会按照请求的顺序（从ZooKeeper的视角看）来分配许可
 **/

public class SemaphoreDemo {
    private static final String ZK_URL = "localhost:2181";
    private static final String SEMAPHORE_PATH = "/demo/semaphore";
    private static final int MAX_LEASES = 2; // 最多2个许可

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3));
        client.start();
        InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, SEMAPHORE_PATH, MAX_LEASES);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Lease>> futures = new ArrayList<>();

        System.out.printf("创建了一个拥有 %d 个许可的信号量。\n", MAX_LEASES);
        System.out.println("3个线程将同时尝试获取许可...");

        for (int i = 0; i < 3; i++) {
            final int threadNum = i + 1;
            Future<Lease> future = executor.submit(() -> {
                System.out.printf("线程 %d: 正在尝试获取许可...\n", threadNum);
                Lease lease = semaphore.acquire(5, TimeUnit.SECONDS);
                if (lease != null) {
                    System.out.printf("✅ 线程 %d: 成功获取许可！\n", threadNum);
                    Thread.sleep(3000); // 模拟持有许可
                    semaphore.returnLease(lease);
                    System.out.printf("⬇️ 线程 %d: 已归还许可。\n", threadNum);
                } else {
                    System.out.printf("❌ 线程 %d: 获取许可超时失败！\n", threadNum);
                }
                return lease;
            });
            futures.add(future);
        }

        long successCount = futures.stream().map(f -> {
            try {
                return f.get();
            } catch (Exception e) {
                return null;
            }
        }).filter(java.util.Objects::nonNull).count();

        System.out.printf("\n--- 结果验证 ---\n在第一轮尝试中，应该有 %d 个线程成功，1个失败。\n", MAX_LEASES);
        System.out.println("实际成功获取许可的线程数: " + successCount);

        executor.shutdown();
        client.close();
        System.out.println("\n--- SemaphoreDemo 结束 ---");
    }
}
