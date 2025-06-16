package com.example.springbootapisign;

import com.example.springbootapisign.service.AppService;
import com.example.springbootapisign.utils.SignUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootApisignApplicationTests {

    @Autowired
    AppService appService;

    @Test
    void contextLoads() {
        String appId = "app1";
        long timeMillis = System.currentTimeMillis();
        String appSecret = appService.getAppKey(appId);
        String sign = SignUtils.signWithHmacSha1(appSecret, appId + "-" + appSecret + "-" + timeMillis);
        System.out.println("timeMillis = " + timeMillis);
        System.out.println("sign = " + sign);
    }

}
