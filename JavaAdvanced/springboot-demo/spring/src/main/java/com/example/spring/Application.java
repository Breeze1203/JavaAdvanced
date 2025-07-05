package com.example.spring;

import com.example.spring.bean.MySpringBean;
import com.example.spring.bean.dependency.ServiceA;
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
        // 从容器中获取Bean
        ServiceA serviceA = run.getBean(ServiceA.class);
        serviceA.doSomethingA();
        EmailService emailService = (EmailService) run.getBean("EmailService");
        emailService.sendEmail("pt","3548297839@qq.com");
        MySpringBean mySpringBean = run.getBean(MySpringBean.class);
        System.out.println("\n--- 关闭 Spring 容器，触发 Bean 销毁 ---");
        // 3. 关闭容器，这将触发单例 Bean 的销毁方法
        run.close();
        System.out.println("--- Spring 容器已关闭 ---");
    }

}
