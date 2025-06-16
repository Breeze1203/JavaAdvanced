package com.example.nacosdiscoveryconsumerfeign.service;

import com.example.nacosdiscoveryconsumerfeign.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class FallbackService {
    public List<Product> getProduct(){
        ArrayList<Product> product = new ArrayList<>();
        product.add(new Product("测试",200,200));
        return product;
    }
}
