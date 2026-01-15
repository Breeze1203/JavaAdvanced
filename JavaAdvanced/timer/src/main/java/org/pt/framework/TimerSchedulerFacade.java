package org.pt.framework;

import org.pt.persistence.TaskPersistence;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName TimerSchedulerFacade
 * @Author pt
 * @Description 任务调度器
 * @Date 2026/1/14 19:34
 **/
public class TimerSchedulerFacade {
    private final HeapTaskScheduler scheduler;
    private final TaskExecutor executor;
    private final TaskPersistence persistence;
    private final ConcurrentHashMap<Long, TaskStatus> taskStatuses = new ConcurrentHashMap<>();

    public TimerSchedulerFacade(int workerThreads, TaskPersistence persistence) {
        // 初始化数据持久化
        this.persistence = persistence;
        // 初始化任务执行器
        this.executor = new TaskExecutor(workerThreads, taskStatuses);
        // 初始化任务调度
        this.scheduler = new HeapTaskScheduler(executor, persistence, taskStatuses);
        loadPendingTasks();  // 启动时加载持久化任务
    }

    private void loadPendingTasks() {
        List<TimerTask> pending = persistence.loadAll();
        for (TimerTask task : pending) {
            if (task.nextExecuteTimeMs > System.currentTimeMillis()) {
                scheduler.scheduleInternal(task);
                taskStatuses.put(task.id, TaskStatus.RUNNABLE);
            }
        }
    }

    public long scheduleAtFixedRate(Runnable command, long initialDelayMs, long periodMs,
                                    int maxRetries, long retryBackoffMs, String name) {
        TimerTask task = new TimerTask(command, initialDelayMs, periodMs, false,
                maxRetries, retryBackoffMs, name);
        scheduler.scheduleInternal(task);
        persistence.save(task);
        taskStatuses.put(task.id, TaskStatus.RUNNABLE);
        return task.id;
    }

    public boolean cancel(long taskId) {
        TaskStatus status = taskStatuses.get(taskId);
        if (status == TaskStatus.RUNNABLE || status == TaskStatus.RUNNING) {
            boolean removed = scheduler.remove(taskId);
            if (removed) {
                taskStatuses.put(taskId, TaskStatus.CANCELLED);
                persistence.delete(taskId);
                return true;
            }
        }
        return false;
    }

    public TaskStatus queryStatus(long taskId) {
        return taskStatuses.getOrDefault(taskId, null);
    }

    public void shutdown() {
        scheduler.shutdown();
        executor.shutdown();
    }
}
