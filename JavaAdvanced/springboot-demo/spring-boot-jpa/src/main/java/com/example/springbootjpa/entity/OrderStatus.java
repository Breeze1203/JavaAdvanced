package com.example.springbootjpa.entity;

import lombok.Getter;

/**
 * @ClassName OrderStatus
 * @Author pt
 * @Description
 * @Date 2024/11/21 13:48
 **/
@Getter
public enum OrderStatus {
    PENDING_PAYMENT(0, "待支付【乘客订单独有状态】"),
    PENDING_DEPARTURE(1, "待出行"),
    CANCELED(2, "已取消（如果需要退款就计算原路退款）"),
    PICKING_UP(3, "接人中【司机独有】"),
    AWAITING_PASSENGER_BOARDING(4, "待乘客上车【司机独有】"),
    IN_TRANSIT(5, "行程中"),
    TRIP_COMPLETED(6, "行程已完成"),
    REFUNDED(7, "已退款（事后纠纷退款【乘客订单独有】）");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    // 根据代码获取枚举实例
    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown OrderStatus code: " + code);
    }
}
