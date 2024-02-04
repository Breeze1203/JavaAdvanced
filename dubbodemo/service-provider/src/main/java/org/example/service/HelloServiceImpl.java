package org.example.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.api.HelloService;
import org.springframework.stereotype.Component;

@Service(interfaceClass = HelloService.class)
@Component
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String message) {
        return "hello"+message;
    }
}
