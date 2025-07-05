package com.pt.springbootrun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MyDemoController
 * @Author pt
 * @Description
 * @Date 2025/7/4 20:53
 **/
@RestController
public class MyDemoController {
    @Autowired
    private MyDemoService myDemoService;

    @GetMapping("/hello")
    public String hello() {
        return myDemoService.getHelloMessage();
    }
}
