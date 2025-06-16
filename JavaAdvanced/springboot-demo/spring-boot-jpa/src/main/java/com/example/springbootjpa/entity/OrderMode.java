package com.example.springbootjpa.entity;

import lombok.Data;
import lombok.Getter;

/*
订单模式
 */
@Getter
public enum OrderMode {
    BRING_GOODS(1,2.0),// 带货
    EXCLUSIVE(2,3.0), // 专享
    GROUP_BUY(3,4.0), // 拼单
    URGENT(4,5.0); // 紧急

    private final int code;
    private final Double priceModePer;

    OrderMode(int code,Double priceModePer) {
        this.code = code;
        this.priceModePer=priceModePer;
    }
    // 根据代码获取枚举实例
    public static OrderMode fromCode(int code) {
        for (OrderMode mode: values()) {
            if (mode.code == code) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown OrderType code: " + code);
    }

}
