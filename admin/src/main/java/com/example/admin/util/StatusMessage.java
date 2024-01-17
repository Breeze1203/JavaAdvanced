package com.example.admin.util;


import lombok.Getter;


/*
请求消息枚举类
 */
@Getter
public enum StatusMessage {
    AUTHENTICATION_FAILED("用户名或密码错误"),
    DELETE_SUCCESS("删除成功"),
    SEND_SUCCESS("发送成功"),
    UPDATE_SUCCESS("修改成功"),
    ADD_SUCCESS("添加成功"),
    LOGIN_SUCCESS("登录成功"),
    LOGIN_FAILED("用户名或密码错误"),
    CODE_EXPIRED("验证码过期,请重新获取"),
    CODE_ERROR("验证码错误,请重新输入"),
    LOGIN_EXISTS("当前已存在登录用户，请稍后再试"),
    WRONG_FORMAT("请输入正确的手机格式"),
    PONE_NOT_REGISTER("该手机号尚未注册，请稍后再试"),
    UNAUTHORIZED_ACCESS("请登录授权"),
    ILLEGAL_PERMISSIONS("你暂无访问权限"),
    LOGIN_OUT_SUCCESS("注销成功"),
    CODE_SUCCESS("验证发送成功,请注意接收"),
    EXISTS_USER("该角色下存在用户，无法删除"),
    DELETE_FAILED("该组织下存在用户，无法删除"),
    NETWORK_ERROR("网络出现异常,请稍后再试");

    private final String message;

    StatusMessage(String message) {
        this.message=message;
    }

}
