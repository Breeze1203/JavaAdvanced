package com.example.interfaceidempotence.idempotent.test;


import com.example.interfaceidempotence.idempotent.annotation.ApiIdempotent;
import com.example.interfaceidempotence.idempotent.common.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 幂等性测试接口
 * Created by double on 2019/7/11.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiIdempotent
    @RequestMapping("testIdempotent")
    public ServerResponse testIdempotent() {
        return ServerResponse.success("test idempotent success");
    }

}