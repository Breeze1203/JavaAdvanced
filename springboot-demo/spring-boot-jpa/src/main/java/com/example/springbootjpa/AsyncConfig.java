package com.example.springbootjpa;

import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @ClassName AsyncConfig
 * @Author pt
 * @Description
 * @Date 2024/12/18 15:09
 **/
@Configuration
public class AsyncConfig{

    @Bean(value = "task")
    public Executor getAsyncExecutor() {
        return new ThreadPoolTaskExecutorBuilder().build();
    }
}
