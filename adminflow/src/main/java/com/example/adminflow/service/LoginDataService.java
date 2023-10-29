package com.example.adminflow.service;

import com.example.adminflow.mapper.LogDataMapper;
import com.example.adminflow.model.LoginData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "LogDataService")
public class LoginDataService {
    @Resource(name = "LogDataMapper")
    LogDataMapper logDataMapper;

    public void insetLoginLog(LoginData loginData){
        logDataMapper.insertLog(loginData);
    }

    public List<LoginData> getAllLog(){
        return logDataMapper.getLogData();
    }
}
