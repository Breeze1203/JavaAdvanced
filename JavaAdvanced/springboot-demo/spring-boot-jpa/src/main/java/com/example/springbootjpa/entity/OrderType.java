package com.example.springbootjpa.entity;

import lombok.Getter;

@Getter
public enum OrderType {
    带货(1),// 带货
    EXCLUSIVE(2), // 专享
    拼单(3), // 拼单
    URGENT(4); // 紧急

    private final int code;

    OrderType(int code) {
        this.code = code;
    }
    // 根据代码获取枚举实例
    public static OrderType fromCode(int code) {
        for (OrderType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown OrderType code: " + code);
    }
}
