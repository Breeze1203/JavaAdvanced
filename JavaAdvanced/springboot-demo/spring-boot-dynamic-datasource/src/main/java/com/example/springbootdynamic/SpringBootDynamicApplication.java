package com.example.springbootdynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = {"com.example.springbootdynamic.dao"})
public class SpringBootDynamicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDynamicApplication.class, args);
    }
}
