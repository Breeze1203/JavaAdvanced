package com.example.admin.controller;

import com.example.admin.model.OperationData;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.OperationDataService;
import com.example.admin.util.LogResult;
import com.example.admin.util.StatusUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Resource(name = "OperationDataService")
    OperationDataService operationDataService;


    @GetMapping("/getAllLog")
    public LogResult getAllLog(@RequestParam("keyword")String keyword, @RequestParam("offset")Integer offset, @RequestParam("pageSize")Integer pageSize){
        List<OperationData> allLog = operationDataService.getAllLog(keyword, offset, pageSize);
        long total = operationDataService.getTotal(keyword);
        return  new LogResult(total,allLog);
    }
    // 删除日志
    @GetMapping("/deleteLog")
    @CheckPermission(permission = "delete_log")
    public StatusUtil deleteLog(){
        Integer i = operationDataService.deleteLog();
        if(i>0){
            return new StatusUtil("清除成功",200,null);
        }else {
            return new StatusUtil("网络出现异常，请稍后再试",500,null);
        }
    }
}
