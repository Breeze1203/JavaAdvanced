package org.pt;

import org.pt.framework.TimerSchedulerFacade;
import org.pt.persistence.TaskPersistence;
import org.pt.persistence.impl.MemoryPersistence;

import java.time.Instant;

/**
 * @ClassName Main
 * @Author pt
 * @Description
 * @Date 2026/1/14 19:45
 **/
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TaskPersistence persistence = new MemoryPersistence();
        TimerSchedulerFacade facade = new TimerSchedulerFacade(4, persistence);

        long id1 = facade.scheduleAtFixedRate(
                () -> System.out.println("Task A: " + Instant.now()),
                1000, 3000, 3, 1000, "Task-A");

        // 模拟失败任务（会重试）
        long id2 = facade.scheduleAtFixedRate(
                () -> { throw new RuntimeException("模拟失败"); },
                2000, 0, 2, 500, "Fail-Task");

        Thread.sleep(10000);
        System.out.println("Status of " + id1 + ": " + facade.queryStatus(id1));
        System.out.println("Status of " + id2 + ": " + facade.queryStatus(id2));
        facade.cancel(id1);

        Thread.sleep(5000);
        facade.shutdown();
        System.out.println("已关闭");
    }
}
