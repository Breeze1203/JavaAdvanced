package org.pt.framework;

/**
 * @ClassName TaskStatus
 * @Author pt
 * @Description 任务状态枚举
 * @Date 2026/1/14 19:28
 **/
public enum TaskStatus {
    RUNNABLE,    // 待执行
    RUNNING,     // 运行中
    COMPLETED,   // 已完成
    FAILED,      // 失败（重试耗尽）
    CANCELLED    // 已取消
}
