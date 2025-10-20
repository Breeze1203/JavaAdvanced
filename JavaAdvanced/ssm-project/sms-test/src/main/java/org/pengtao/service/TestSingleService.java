package org.pengtao.service;

import org.pengtao.SmsRequest;
import org.pengtao.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestSingleService {
    // 注入的是单一实例
    private final SmsService smsService;

    @Autowired
    public TestSingleService(SmsService smsService) {
        this.smsService = smsService;
    }

    public void testSend() {
        smsService.send(new SmsRequest("12345", "T_001", Map.of()));
    }
}
