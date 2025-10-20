package org.pengtao.service;

import org.pengtao.SmsFactory;
import org.pengtao.SmsRequest;
import org.pengtao.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestFactoryService {
    private final SmsFactory smsFactory; // 注入的是工厂

    @Autowired
    public TestFactoryService(SmsFactory smsFactory) {
        this.smsFactory = smsFactory;
    }

    public void testSend() {
        // 动态选择阿里云
        SmsService aliyun = smsFactory.getService("aliyun");
        aliyun.send(new SmsRequest("12345", "T_001", Map.of()));
    }
}
