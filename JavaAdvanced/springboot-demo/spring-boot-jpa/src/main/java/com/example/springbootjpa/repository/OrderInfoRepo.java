package com.example.springbootjpa.repository;

import com.example.springbootjpa.entity.OrderInfoModel;
import org.springframework.data.repository.Repository;

public interface OrderInfoRepo extends Repository<OrderInfoModel, Long> {
}
