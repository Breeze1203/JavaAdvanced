package com.example.spring;

import com.example.spring.event.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.format.datetime.DateFormatter;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        ApplicationStartup applicationStartup = run.getApplicationStartup();
        EmailService emailService = (EmailService) run.getBean("EmailService");
        emailService.sendEmail("pt","3548297839@qq.com");
    }

}
