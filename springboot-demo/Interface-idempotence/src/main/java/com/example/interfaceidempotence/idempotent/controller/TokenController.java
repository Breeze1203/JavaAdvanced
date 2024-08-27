package com.example.interfaceidempotence.idempotent.controller;

/**
 * Created by double on 2019/7/11.
 */

import com.example.interfaceidempotence.idempotent.common.ServerResponse;
import com.example.interfaceidempotence.idempotent.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/getToken")
    public ServerResponse token() {
        return tokenService.createToken();
    }

}