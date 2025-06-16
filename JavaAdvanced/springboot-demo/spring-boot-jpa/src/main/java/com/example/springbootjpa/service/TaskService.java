package com.example.springbootjpa.service;

import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

/**
 * @ClassName TaskService
 * @Author pt
 * @Description
 * @Date 2024/12/18 15:03
 **/
@Service
public class TaskService {
    @Resource(name = "task")
    private Executor task;

    public void asyncTask() {
       task.execute(()->{
           System.out.println("Async task is running with thread: " + Thread.currentThread().getName());
       });
    }

    @Async
    public void asyncTaskTwo() {
        // 异步任务逻辑
        System.out.println("Async task two is running with thread: " + Thread.currentThread().getName());
    }


}
