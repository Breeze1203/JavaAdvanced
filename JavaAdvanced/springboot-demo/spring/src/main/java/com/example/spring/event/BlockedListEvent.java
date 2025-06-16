package com.example.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName BlockedListEvent
 * @Author pt
 * @Description
 * @Date 2024/11/15 11:37
 **/
public class BlockedListEvent extends ApplicationEvent {

    private final String address;
    private final String content;

    public BlockedListEvent(Object source, String address, String content) {
        super(source);
        this.address = address;
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "BlockedListEvent{" +
                "address='" + address + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}