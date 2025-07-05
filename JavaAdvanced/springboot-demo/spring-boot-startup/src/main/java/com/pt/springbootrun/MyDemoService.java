package com.pt.springbootrun;

import org.springframework.stereotype.Service;

/**
 * @ClassName MyDemoService
 * @Author pt
 * @Description
 * @Date 2025/7/4 20:52
 **/
@Service
public class MyDemoService {
    public String getHelloMessage() {
        return "Hello from MyDemoService!";
    }
}
