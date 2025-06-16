package com.example.nacosdiscoveryconsumerfeign.controller;


import com.example.nacosdiscoveryconsumerfeign.entity.Product;
import com.example.nacosdiscoveryconsumerfeign.feign.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    Consumer consumer;

    @PostMapping("/getProduct")
    public List<Product> getProduct(){
        return consumer.getProduct();
    }
}
