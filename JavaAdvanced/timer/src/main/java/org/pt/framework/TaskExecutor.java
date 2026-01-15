package org.pt.framework;

import java.util.concurrent.*;

/**
 * @ClassName TaskExecutor
 * @Author pt
 * @Description 任务执行器
 * @Date 2026/1/14 19:38
 **/
public class TaskExecutor {
    private final ExecutorService pool;
    private final ConcurrentHashMap<Long, TaskStatus> statuses;
    private static final long EXEC_TIMEOUT_MS = 10_000;  // 默认超时

    TaskExecutor(int threads, ConcurrentHashMap<Long, TaskStatus> statuses) {
        this.pool = Executors.newFixedThreadPool(threads);
        this.statuses = statuses;
    }

    void executeTask(TimerTask task, Callback callback) {
        pool.submit(() -> {
            boolean success = false;
            try {
                Future<?> future = pool.submit(task.command);
                future.get(EXEC_TIMEOUT_MS, TimeUnit.MILLISECONDS);  // 超时保护
                success = true;
            } catch (TimeoutException e) {
                System.out.println("Timeout for " + task);
            } catch (Exception e) {
                System.out.println("Error in " + task + ": " + e.getMessage());
            } finally {
                callback.onComplete(task, success);
            }
        });
    }

    void shutdown() {
        pool.shutdown();
    }

    @FunctionalInterface
    interface Callback {
        void onComplete(TimerTask task, boolean success);
    }
}
