package org.example.controller;

import org.example.pojo.Product;
import org.example.service.ProductService;
import org.example.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @GetMapping("/list")
    private List<Product> selectProductList(){
        return productServiceImpl.setlectProductList();
    }

    @PostMapping("/save")
    private Map<Object,Object> createProduct(@RequestBody Product product){
        return productServiceImpl.createProduct(product);
    }
}
