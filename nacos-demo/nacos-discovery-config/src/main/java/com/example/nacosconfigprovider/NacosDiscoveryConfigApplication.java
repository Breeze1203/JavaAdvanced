package com.example.nacosconfigprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class NacosDiscoveryConfigApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(NacosDiscoveryConfigApplication.class, args);
        String username = run.getEnvironment().getProperty("test");
        System.out.println(username);
    }

}
