package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 用于启用断路器功能
 */
@SpringBootApplication
@EnableCircuitBreaker
public class HystrixApplication {
    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class);
    }
}
