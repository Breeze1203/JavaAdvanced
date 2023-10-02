package org.example.service;

import org.example.pojo.Order;

public interface OrderService {
    Order selectOrderById(Integer id);

}
