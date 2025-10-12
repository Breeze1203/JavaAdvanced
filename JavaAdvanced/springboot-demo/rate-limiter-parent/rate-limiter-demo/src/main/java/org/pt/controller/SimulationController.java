package org.pt.controller;

import org.pt.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SimulationController
 * @Author pt
 * @Description
 * @Date 2025/10/9 21:35
 **/
@RestController
public class SimulationController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    /**
     * 实验对象A：令牌桶 (Token Bucket)
     * 容量10，平均速率 2个/秒
     */
    @GetMapping("/token")
    @RateLimiter(limit = 10, period = 5, timeUnit = TimeUnit.SECONDS, algorithm = "redisTokenBucket")
    public String tokenBucketTest() {
        String currentTime = LocalTime.now().format(formatter);
        System.out.println("✅ [Token Bucket] Request Succeeded at: " + currentTime);
        return "Token Bucket OK at " + currentTime;
    }

    /**
     * 实验对象B：漏桶 (Leaky Bucket)
     * 容量10，固定速率 2个/秒
     */
    @GetMapping("/leaky")
    @RateLimiter(limit = 10, period = 5, timeUnit = TimeUnit.SECONDS, algorithm = "leakyBucket")
    public String leakyBucketTest() {
        String currentTime = LocalTime.now().format(formatter);
        System.out.println("💧 [Leaky Bucket] Request Succeeded at: " + currentTime);
        return "Leaky Bucket OK at " + currentTime;
    }
}