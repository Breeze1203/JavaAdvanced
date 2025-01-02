package com.example.springbootjpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OrderInfoRepo
 * @Author pt
 * @Description 订单详细信息实体
 * @Date 2024/11/21 11:09
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderInfoModel extends BaseEntity {
    private String orderId; // 订单ID
    private Long uId; //用户id
    private String time; // 下单时间
    private String content; // 订单内容
    private String fromLocation; //出发地
    private String toLocation; //目的地
    private Double weight; //重量
    private Double length;//长度
    private Double width;//宽度
    private Double height;//高度
    private OrderType type; // 订单类型
    private String longitude;//经度
    private String latitude;//纬度
    //private VehicleType vehicleType; //车辆类型

}
