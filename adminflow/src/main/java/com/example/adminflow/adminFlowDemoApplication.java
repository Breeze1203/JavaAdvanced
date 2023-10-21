package com.example.adminflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = "com.example.adminflow.mapper")
@SpringBootApplication
public class adminFlowDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(adminFlowDemoApplication.class, args);
    }

}
