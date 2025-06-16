package com.example.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName BlockedListNotifier
 * @Author pt
 * @Description
 * @Date 2024/11/15 11:41
 **/
@Service(value = "BlockedListNotifier")
public class BlockedListNotifier implements ApplicationListener<BlockedListEvent> {

    private String notificationAddress;

    public void setNotificationAddress(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    @Async
    public void onApplicationEvent(BlockedListEvent event) {
        System.out.println("接收邮件的线程"+Thread.currentThread().getName());
        System.out.println(event.toString());
    }
}
