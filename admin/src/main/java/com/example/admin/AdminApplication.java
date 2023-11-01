package com.example.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.admin.mapper")
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class);
	}

}
