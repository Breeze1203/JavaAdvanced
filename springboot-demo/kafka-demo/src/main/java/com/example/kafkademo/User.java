package com.example.kafkademo;

import java.util.Date;

/**
 * @ClassName User
 * @Author pt
 * @Description
 * @Date 2025/1/16 13:36
 **/
public class User {
    private Channel channel;

    private Integer code;

    private String name;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "channel=" + channel +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
