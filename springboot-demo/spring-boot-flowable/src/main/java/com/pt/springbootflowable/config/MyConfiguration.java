package com.pt.springbootflowable.config;

import org.flowable.spring.boot.cmmn.Cmmn;
import org.flowable.spring.boot.process.Process;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * @ClassName MyConfiguration
 * @Author pt
 * @Description
 * @Date 2024/12/31 11:11
 **/
@Configuration
public class MyConfiguration {

    @Process
    @Bean
    public TaskExecutor processTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Cmmn
    @Bean
    public TaskExecutor cmmnTaskExecutor() {
        return new SyncTaskExecutor();
    }
}
