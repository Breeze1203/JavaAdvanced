package com.example.interfaceidempotence.idempotent.service;


import com.example.interfaceidempotence.idempotent.common.ServerResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Created by double on 2019/7/11.
 */
public interface TokenService {

    ServerResponse createToken();

    void checkToken(HttpServletRequest request) ;
}
