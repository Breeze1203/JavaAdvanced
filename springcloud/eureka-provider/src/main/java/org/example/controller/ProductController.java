package org.example.controller;

import org.example.pojo.Product;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProductController {
    @Resource(name = "ProductService")
    ProductService productService;

    @GetMapping("/list")
    public List<Product> getProductList(){
        return productService.getById(1);
    }
    @GetMapping("/product")
    String hello(String name){
        System.out.println(name);
        return name;
    }

    @PostMapping("/product1")
    Product addProduct(@RequestBody Product product){
        return product;
    }

    //其中{id}表示URL路径中的参数@PathVariable("id")注解将该路径参数绑定到方法的id参数上，
    @DeleteMapping("/product2/{id}")
    void deleteProductById(@PathVariable("id")Integer id){
        System.out.println(id);
    }

    //RequestHeader("name")注解将HTTP请求头参数的值绑定到方法的参数上
    @GetMapping("/product3")
    void getProductByName(@RequestHeader("name") String name){
        System.out.println(name);
    }
}
