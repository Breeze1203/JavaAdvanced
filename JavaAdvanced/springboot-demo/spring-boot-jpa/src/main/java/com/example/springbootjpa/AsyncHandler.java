package com.example.springbootjpa;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * @ClassName AsyncHandler
 * @Author pt
 * @Description
 * @Date 2024/12/18 15:13
 **/
public class AsyncHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {

    }
}
