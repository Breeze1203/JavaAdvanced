package com.example.spring.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName EmailService
 * @Author pt
 * @Description
 * @Date 2024/11/15 11:39
 **/
@Service(value = "EmailService")
public class EmailService implements ApplicationEventPublisherAware {

    private final List<String> blockedList= Arrays.asList("pt", "item2", "item3");;
    private ApplicationEventPublisher publisher;


    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Async
    public void sendEmail(String address, String content) {
        System.out.println("发邮件的线程"+Thread.currentThread().getName());
        if (blockedList.contains(address)) {
            publisher.publishEvent(new BlockedListEvent(this, address, content));
            return;
        }
    }
}
