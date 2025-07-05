package com.pt.springbootrun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyCommandLineRunner
 * @Author pt
 * @Description
 * @Date 2025/7/4 20:54
 **/
@Component
@Order(1) // 指定执行顺序
public class MyCommandLineRunner implements CommandLineRunner {
    @Autowired
    private MyDemoService myDemoService;

    @Value("${demo.app.name}")
    private String appName;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====== [Runner] @Order(1) MyCommandLineRunner 执行 ======");
        System.out.println("====== [Runner] 读取到配置 appName: " + appName);
        System.out.println("====== [Runner] 调用Service: " + myDemoService.getHelloMessage());
    }
}

@Component
@Order(2)
class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("====== [Runner] @Order(2) MyApplicationRunner 执行 ======");
    }
}
