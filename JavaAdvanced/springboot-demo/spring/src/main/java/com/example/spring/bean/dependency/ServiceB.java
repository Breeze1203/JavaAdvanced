package com.example.spring.bean.dependency;

/**
 * @ClassName ServiceB
 * @Author pt
 * @Description
 * @Date 2025/7/3 20:09
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    // B 依赖 A
    private ServiceA serviceA;

    @Autowired
    public void setServiceA(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public ServiceB() {
        System.out.println("ServiceB: 构造方法执行");
    }

    public void doSomethingB() {
        System.out.println("执行ServiceB的方法");
    }
}
