package com.example.spring.service;

import org.springframework.stereotype.Service;

/**
 * @ClassName StudentService
 * @Author pt
 * @Description
 * @Date 2024/11/14 17:01
 **/
@Service(value = "stuService")
public class StudentService {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
