package com.example.adminflow.controller;

import com.example.adminflow.model.OperationData;
import com.example.adminflow.service.OperationDataService;
import com.example.adminflow.util.LogResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    @Resource(name = "OperationDataService")
    OperationDataService operationDataService;


    @GetMapping("/getAllLog")
    public LogResult getAllLog(@RequestParam("keyword")String keyword, @RequestParam("offset")Integer offset, @RequestParam("pageSize")Integer pageSize){
        List<OperationData> allLog = operationDataService.getAllLog(keyword, offset, pageSize);
        long total = operationDataService.getTotal(keyword);
        LogResult logResult = new LogResult();
        logResult.setTotal(total);
        logResult.setOperationData(allLog);
        return logResult;
    }
}
