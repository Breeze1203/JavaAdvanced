package org.pt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LogController
 * @Author pt
 * @Description
 * @Date 2025/8/31 21:33
 **/
@RestController
public class LogController {
    @Autowired
    private LogProducerService producerService;

    @GetMapping("/log")
    public String log(@RequestParam String message) {
        producerService.sendLog("INFO", message);
        return "Log sent!";
    }
}
