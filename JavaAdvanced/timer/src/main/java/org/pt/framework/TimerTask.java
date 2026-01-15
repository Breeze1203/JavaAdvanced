package org.pt.framework;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName TimerTask
 * @Author pt
 * @Description 任务实体
 * @Date 2026/1/14 19:29
 **/
public class TimerTask implements Comparable<TimerTask>, Serializable {

    private static final long serialVersionUID = 1L;
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public final long id; // 任务id
    final String name; // 任务名称
    final Runnable command;
    long nextExecuteTimeMs; // 下次执行时间
    final long periodMs; // 周期
    final boolean fixedDelay; // 固定延迟
    final int maxRetries; // 重试次数
    final long retryBackoffMs;  // 重试间隔，指数增长
    transient AtomicInteger retryCount = new AtomicInteger(0);
    transient AtomicBoolean cancelled = new AtomicBoolean(false); // 是否被取消

    TimerTask(Runnable command, long initialDelayMs, long periodMs, boolean fixedDelay,
              int maxRetries, long retryBackoffMs, String name) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.command = command;
        this.nextExecuteTimeMs = System.currentTimeMillis() + initialDelayMs;
        this.periodMs = periodMs;
        this.fixedDelay = fixedDelay;
        this.maxRetries = maxRetries;
        this.retryBackoffMs = retryBackoffMs;
        this.name = name != null ? name : "Task-" + id;
    }

    @Override
    public int compareTo(TimerTask o) {
        return Long.compare(this.nextExecuteTimeMs, o.nextExecuteTimeMs);
    }

    boolean isCancelled() {
        return cancelled.get();
    }

    void cancel() {
        cancelled.set(true);
    }

    // 计算下次执行时间
    boolean reschedule(boolean afterFailure) {
        if (periodMs <= 0 || isCancelled()) {
            return false;
        }
        if (afterFailure) {
            // 重试：指数退避
            int attempts = retryCount.get();
            nextExecuteTimeMs = System.currentTimeMillis() + (retryBackoffMs * (long) Math.pow(2, attempts - 1));
        } else if (fixedDelay) {
            nextExecuteTimeMs = System.currentTimeMillis() + periodMs;
        } else {
            nextExecuteTimeMs += periodMs;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%d, next=%s, period=%dms, %s, retries=%d/%d]",
                name, id, Instant.ofEpochMilli(nextExecuteTimeMs), periodMs,
                fixedDelay ? "fixed-delay" : "fixed-rate", retryCount.get(), maxRetries);
    }
}
