package com.example.springbootdynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.springbootdynamic.dao"})
public class SpringBootAffairsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAffairsApplication.class, args);
    }
}
