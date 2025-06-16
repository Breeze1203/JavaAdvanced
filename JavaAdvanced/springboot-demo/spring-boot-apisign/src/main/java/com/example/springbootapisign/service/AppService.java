package com.example.springbootapisign.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppService {

    private static final Map<String, String> APP_INFO = Map.of("app1", "sign1", "app2", "sign2");

    public String getAppKey(String appId) {
        return APP_INFO.get(appId);
    }
}
