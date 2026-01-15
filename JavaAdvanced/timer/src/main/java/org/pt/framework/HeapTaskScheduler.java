package org.pt.framework;

import org.pt.persistence.TaskPersistence;

import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName HeapTaskScheduler
 * @Author pt
 * @Description 调度层
 * @Date 2026/1/14 19:37
 **/
public class HeapTaskScheduler {
    private final PriorityQueue<TimerTask> queue = new PriorityQueue<>();
    private final ConcurrentHashMap<Long, TimerTask> taskMap = new ConcurrentHashMap<>();  // 快速查找/取消
    private final Thread schedulerThread;
    private final TaskExecutor executor;
    private final TaskPersistence persistence;
    private final ConcurrentHashMap<Long, TaskStatus> statuses;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Object lock = new Object();

    // 时间补偿基准
    private volatile long nanoBase = System.nanoTime();
    private volatile long milliBase = System.currentTimeMillis();

    HeapTaskScheduler(TaskExecutor executor, TaskPersistence persistence,
                      ConcurrentHashMap<Long, TaskStatus> statuses) {
        this.executor = executor;
        this.persistence = persistence;
        this.statuses = statuses;
        // 开启一个后台线程轮询任务
        schedulerThread = new Thread(this::scheduleLoop, "Scheduler-Thread");
        schedulerThread.setDaemon(true);
        schedulerThread.start();
    }

    void scheduleInternal(TimerTask task) {
        synchronized (lock) {
            queue.offer(task);
            taskMap.put(task.id, task);
            lock.notify();
        }
    }

    boolean remove(long taskId) {
        synchronized (lock) {
            TimerTask task = taskMap.remove(taskId);
            if (task != null) {
                task.cancel();
                queue.remove(task);  // O(n), 但有 map 辅助
                return true;
            }
            return false;
        }
    }

    private void scheduleLoop() {
        while (running.get()) {
            TimerTask task = null;
            long nowMs;
            synchronized (lock) {
                nowMs = currentTimeMillisWithDriftCorrection();
                while (running.get() && (queue.isEmpty() || queue.peek().nextExecuteTimeMs > nowMs)) {
                    try {
                        if (queue.isEmpty()) {
                            lock.wait();
                        } else {
                            long waitMs = queue.peek().nextExecuteTimeMs - nowMs;
                            if (waitMs > 0) {
                                lock.wait(waitMs);
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    nowMs = currentTimeMillisWithDriftCorrection();
                }

                if (!running.get()) break;

                task = queue.poll();
                if (task != null && task.isCancelled()) {
                    taskMap.remove(task.id);
                    continue;
                }
            }

            if (task != null) {
                statuses.put(task.id, TaskStatus.RUNNING);
                executor.executeTask(task, this::onTaskComplete);
            }
        }
    }

    private void onTaskComplete(TimerTask task, boolean success) {
        synchronized (lock) {
            if (!success && task.retryCount.incrementAndGet() <= task.maxRetries) {
                // 重试
                task.reschedule(true);
                queue.offer(task);
                statuses.put(task.id, TaskStatus.RUNNABLE);
                persistence.update(task);
                System.out.println("Retry " + task.retryCount.get() + "/" + task.maxRetries + " for " + task);
            } else {
                // 完成或失败
                statuses.put(task.id, success ? TaskStatus.COMPLETED : TaskStatus.FAILED);
                taskMap.remove(task.id);
                if (task.periodMs > 0 && success && !task.isCancelled()) {
                    task.retryCount.set(0);  // 重置重试计数
                    task.reschedule(false);
                    queue.offer(task);
                    taskMap.put(task.id, task);
                    statuses.put(task.id, TaskStatus.RUNNABLE);
                    persistence.update(task);
                } else {
                    persistence.delete(task.id);
                }
            }
            lock.notify();
        }
    }

    void shutdown() {
        running.set(false);
        synchronized (lock) {
            lock.notify();
        }
    }

    private long currentTimeMillisWithDriftCorrection() {
        long nanoNow = System.nanoTime();
        long milliNow = System.currentTimeMillis();
        if (Math.abs(milliNow - milliBase) > 5000) {
            nanoBase = nanoNow;
            milliBase = milliNow;
        }
        long elapsedMs = (nanoNow - nanoBase) / 1_000_000;
        return milliBase + elapsedMs;
    }
}
