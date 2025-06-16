package org.pt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(Main.class);
        DistributedLockService service= (DistributedLockService) run.getBean("DistributedLockService");
        service.processOrder("0001");

        SlidingWindowRateLimiterService rateLimiter = run.getBean(SlidingWindowRateLimiterService.class);
        String userId = "pengtao";
        // 连续快速地模拟5次请求
        for (int i = 0; i < 5; i++) {
            System.out.print("第 " + (i + 1) + " 次请求 -> ");
            rateLimiter.handleApiRequest(userId);
            Thread.sleep(1000); // 每次请求间隔1秒
        }
        System.out.println("\n...等待 8 秒，让部分窗口滑动过去...\n");
        Thread.sleep(8000);
        // 再次尝试请求
        System.out.print("等待后的第一次请求 -> ");
        rateLimiter.handleApiRequest(userId);
        run.close();
    }
}