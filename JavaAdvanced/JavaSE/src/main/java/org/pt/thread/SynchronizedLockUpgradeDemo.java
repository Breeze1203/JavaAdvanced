package org.pt.thread;

/**
 * @ClassName SynchronizedLockUpgradeDemo
 * @Author pt
 * @Description
 * @Date 2025/7/5 21:07
 **/

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class SynchronizedLockUpgradeDemo {

    public static void main(String[] args) throws InterruptedException {

        // ==================================================================
        // 场景一：偏向锁 (Biased Locking)
        // ==================================================================
        System.out.println("====== 场景一：偏向锁 ======");
        // JVM 默认有4-5秒的偏向锁延迟启动，所以这里先等待5秒 目的是为了确保应用启动时，偏向锁功能是激活的
        TimeUnit.SECONDS.sleep(5);
        Object lockObject = new Object();
        System.out.println("【初始状态】创建新对象后，锁状态：");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        // 进入同步代码块，触发偏向锁
        synchronized (lockObject) {
            System.out.println("【加锁后】线程进入同步块，锁状态：");
            System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        }

        System.out.println("【释放锁后】线程退出同步块，但锁信息仍保留：");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        System.out.println("\n");
        TimeUnit.SECONDS.sleep(2);
        // ==================================================================
        // 场景二：轻量级锁 (Lightweight Locking)
        // ==================================================================
        System.out.println("====== 场景二：升级为轻量级锁 ======");
        // 创建另一个线程来竞争锁，触发偏向锁的撤销和升级
        Thread competitorThread = new Thread(() -> {
            synchronized (lockObject) {
                System.out.println("【竞争线程】新线程获取锁，锁状态：");
                System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
            }
        });

        competitorThread.start();
        competitorThread.join(); // 等待竞争线程执行完毕
        System.out.println("【竞争后】竞争线程释放锁，锁状态：");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());

        System.out.println("\n");
        TimeUnit.SECONDS.sleep(2);

        // ==================================================================
        // 场景三：重量级锁 (Heavyweight Locking)
        // ==================================================================
        System.out.println("====== 场景三：升级为重量级锁 ======");
        // 我们需要创造激烈的锁竞争
        Thread thread1 = new Thread(() -> {
            synchronized (lockObject) {
                System.out.println("【线程1】获取锁，并持有3秒...");
                try {
                    // 持有锁，让线程2有充分的时间来竞争
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("【线程1】持有锁期间，锁状态：");
                    System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            // 睡眠1秒，确保thread1先拿到锁
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockObject) {
                System.out.println("【线程2】终于获取到锁，锁状态：");
                System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("【所有竞争结束后】锁状态：");
        System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
    }
}
