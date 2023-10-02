package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *负载均衡默认是轮询的策略
 */


@EnableFeignClients
@SpringBootApplication
public class EurekaConsumerApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(EurekaConsumerApplication.class);
    }
}
