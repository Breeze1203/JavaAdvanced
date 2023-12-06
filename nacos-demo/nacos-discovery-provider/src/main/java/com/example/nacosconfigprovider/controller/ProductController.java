package com.example.nacosconfigprovider.controller;

import com.example.nacosconfigprovider.entity.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @PostMapping("/product")
    public List<Product> getProduct(){
        List<Product> list=new ArrayList<>();
        Product p1 = new Product("华为",3000,200);
        Product p2 = new Product("苹果", 6000, 100);
        list.add(p2);
        list.add(p1);
        return list;
    }
}
