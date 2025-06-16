package org.example.service;

import org.example.pojo.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    /*
    根据主键查询订单
     */
     List<Product> selectProductById(Integer id);
}
