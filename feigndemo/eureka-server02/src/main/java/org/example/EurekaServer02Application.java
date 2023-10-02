package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer02Application
{
    public static void main( String[] args )
    {
        SpringApplication.run(EurekaServer02Application.class);
    }
}
