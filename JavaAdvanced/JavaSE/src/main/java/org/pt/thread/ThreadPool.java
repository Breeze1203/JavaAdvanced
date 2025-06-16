package org.pt.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPool {
    public static void main(String[] args) {
        // 1.创建线程池对象；创建单个的线程池对象
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 2.创建固定数量的线程池（核心线程数数量，核心线程数为2
        ExecutorService executorService1 = Executors.newFixedThreadPool(2);
        // 3.创建一个按照计划执行的线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        // 4.创建一个自动增长的线程池
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                executorService1.execute(()->{
                    System.out.println("Executors创建线程池的方式实现多线程....");
                    int j=100/3;
                    System.out.println(j);
                });
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            executorService1.shutdown();
        }
    }
}
