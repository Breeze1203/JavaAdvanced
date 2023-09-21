package org.example.controller;

import org.example.pojo.Product;
import org.example.service.ProductService;
import org.example.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @GetMapping("/list")
    private List<Product> selectProductList(){
        return productServiceImpl.setlectProductList();
    }
}
