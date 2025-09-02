package org.pt.thread;

/**
 * @ClassName MyThreadPool
 * @Author pt
 * @Description
 * @Date 2025/7/13 20:31
 **/
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 一个用于理解原理的简化版手写线程池
 */
public class MyThreadPool {

    private final BlockingQueue<Runnable> workQueue;

    private final List<WorkerThread> workers = new ArrayList<>();

    private volatile boolean isShutdown = false;

    /**
     * 构造函数
     *
     * @param coreSize 核心线程数量
     */
    public MyThreadPool(int coreSize) {
        // 初始化任务队列
        this.workQueue = new LinkedBlockingQueue<>();
        // 创建并启动核心数量的工作线程
        for (int i = 0; i < coreSize; i++) {
            WorkerThread worker = new WorkerThread();
            workers.add(worker);
            worker.start();
        }
    }

    /**
     * 3. 执行入口：向线程池提交任务
     *
     * @param task 要执行的任务
     * @throws InterruptedException
     */
    public void execute(Runnable task) throws InterruptedException {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool has been shut down.");
        }
        // 将任务放入阻塞队列
        workQueue.put(task);
    }

    /**
     * 4. 关闭机制：关闭线程池
     */
    public void shutdown() {
        this.isShutdown = true;
        // 中断所有正在工作或等待的线程
        for (Thread worker : workers) {
            // 如果线程正在workQueue.take()上阻塞，中断会使其抛出InterruptedException
            worker.interrupt();
        }
    }

    /**
     * 内部类：定义工作线程的逻辑
     */
    private class WorkerThread extends Thread {
        @Override
        public void run() {
            // 循环地从任务队列中获取任务并执行
            // 当线程池未关闭 或 队列中仍有任务时，继续执行
            while (!isShutdown || !workQueue.isEmpty()) {
                Runnable task = null;
                try {
                    // 阻塞式地从队列中获取任务
                    // 如果线程池关闭且队列为空，take()会一直阻塞，所以需要interrupt来唤醒
                    if (isShutdown && workQueue.isEmpty()) {
                        break; // 确保在关闭后能退出循环
                    }
                    task = workQueue.take();
                } catch (InterruptedException e) {
                    // 接收到中断信号，通常意味着线程池要关闭了
                    // 清理中断状态，并准备退出循环
                    Thread.currentThread().interrupt(); // 重新设置中断状态
                    break;
                }

                if (task != null) {
                    try {
                        // 执行任务
                        task.run();
                    } catch (RuntimeException e) {
                        // 任务执行时可能抛出异常，这里简单打印，防止工作线程因此死亡
                        System.err.println("Error while executing task: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建一个拥有3个核心线程的线程池
        MyThreadPool pool = new MyThreadPool(3);
        // 提交10个任务到线程池
        for (int i = 0; i < 10; i++) {
            final int taskNumber = i;
            pool.execute(() -> {
                System.out.println("任务 " + taskNumber + " 正在由线程 " + Thread.currentThread().getName() + " 执行");
                try {
                    // 模拟任务执行耗时
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                System.out.println("--- 任务 " + taskNumber + " 执行完毕 ---");
            });
        }
        // 等待一段时间，让任务有时间执行
        System.out.println("所有任务已提交，等待执行...");
        Thread.sleep(5000);

        // 关闭线程池
        System.out.println("准备关闭线程池...");
        pool.shutdown();
        System.out.println("线程池已关闭。");
    }

}