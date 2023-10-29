package com.example.adminflow.controller;

import com.example.adminflow.model.LoginData;
import com.example.adminflow.service.LoginDataService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    @Resource(name = "LogDataService")
    LoginDataService loginDataService;


    @GetMapping("/getAllLog")
    public List<LoginData> getAllLog(){
        return loginDataService.getAllLog();
    }
}
