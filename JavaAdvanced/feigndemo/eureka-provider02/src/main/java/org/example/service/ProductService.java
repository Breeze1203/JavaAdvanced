package org.example.service;

import org.example.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> setlectProductList();
    /*
    新增商品
     */
    Map<Object,Object> createProduct(Product product);
}
