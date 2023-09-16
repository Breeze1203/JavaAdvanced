package org.example.service;

import org.example.pojo.Product;

import java.util.List;

/*
商品服务
 */
public interface ProductService {
    /*
    查询商品列表
     */
    List<Product> selectProductList();
}
