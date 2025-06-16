package com.example.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@MapperScan(basePackages = "com.example.admin.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(AdminApplication.class, args);
	}

}
