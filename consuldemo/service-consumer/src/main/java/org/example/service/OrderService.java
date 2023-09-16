package org.example.service;

import org.example.pojo.Order;
import org.example.pojo.Product;

import java.util.List;


public interface OrderService {
    /*
    根据主键查询订单
     */
     Order selectOrderById(Integer id);
}
