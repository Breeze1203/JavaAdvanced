package com.example.spring.bean.dependency;

/**
 * @ClassName ServiceA
 * @Author pt
 * @Description
 * @Date 2025/7/3 20:08
 **/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {

    // A 依赖 B
    private ServiceB serviceB;

    @Autowired
    public void setServiceB(ServiceB serviceB) {
        this.serviceB = serviceB;
    }

    public ServiceA() {
        System.out.println("ServiceA: 构造方法执行");
    }

    public void doSomethingA() {
        System.out.println("执行ServiceA的方法，并调用ServiceB -> " + serviceB.getClass().getName());
        serviceB.doSomethingB();
    }
}
