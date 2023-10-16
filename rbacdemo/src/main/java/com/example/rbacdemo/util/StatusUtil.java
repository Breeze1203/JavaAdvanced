package com.example.rbacdemo.util;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusUtil {
    private String message;
    private int code;
    public static final String  success_message="登录成功";

    public static final String  failure_message="用户名或密码错误";


}
