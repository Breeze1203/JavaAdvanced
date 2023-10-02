package org.example.controller;

import org.example.pojo.Product;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource(name = "ProductService")
    ProductService productService;

    @GetMapping("/list")
    public List<Product> getProductList(){
        return productService.getById(1);
    }
}
