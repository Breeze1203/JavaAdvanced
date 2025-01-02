package com.example.springbootjpa;

import com.example.springbootjpa.entity.OrderModel;
import com.example.springbootjpa.entity.OrderType;
import com.example.springbootjpa.repository.OrderInfoRepo;
import com.example.springbootjpa.repository.OrderRepo;
import com.example.springbootjpa.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class SpringBootJpaApplicationTests {

    @Autowired
    private TaskService taskService;

    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        taskService.asyncTaskTwo();
        System.out.println("-----------运行---------");
    }

}
