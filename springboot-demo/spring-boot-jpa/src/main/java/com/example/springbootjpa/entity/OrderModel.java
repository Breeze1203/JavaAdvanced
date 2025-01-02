package com.example.springbootjpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OrderModel
 * @Author pt
 * @Description 订单表
 * @Date 2024/11/21 10:50
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderModel extends BaseEntity {
    private Long uid; // 用户ID
    private String time; // 更新时间
    private OrderType type; // 类型：1-乘客，2-司机，3-第三方
    private OrderStatus state;// 状态：0-待支付，1-待出行，2-已取消
    private OrderMode mode;
    private String orderId; // 订单ID
    private Long price;//订单价格
    //private VehicleType vehicleType; //车辆类型
}
