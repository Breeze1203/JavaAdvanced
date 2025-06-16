package com.example.springbootjpa.repository;

import com.example.springbootjpa.entity.OrderModel;
import org.springframework.data.repository.Repository;


public interface OrderRepo extends Repository<OrderModel, Long> {
   OrderModel save(OrderModel orderModel);
   OrderModel findById(Long id);
}
