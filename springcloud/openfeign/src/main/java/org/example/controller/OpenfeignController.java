package org.example.controller;

import org.example.pojo.Product;
import org.example.service.OpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/openfeign")
public class OpenfeignController {

    @Qualifier("org.example.service.OpenFeignService")
    @Autowired
    OpenFeignService openFeignService;

    @GetMapping("/")
    public List<Product> getPro(){
        return openFeignService.getProduct();
    }

    @GetMapping("/name")
    public String hello(String name){
        String hello = openFeignService.hello(name);
        return hello;
    }

    // 这里json格式转为对象要加上@RequestBody
    @PostMapping("/product1")
    public Product addProduct(@RequestBody Product product){
        Product product1 = openFeignService.addProduct(product);
        System.out.println(product1);
        return product1;
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id")Integer id){
        System.out.println("id:"+id);
        openFeignService.deleteProductById(id);
    }

    @GetMapping("/getProductByName")
    public void getProductByName(){
        try {
//            URL编码是一种将特殊字符和非ASCII字符转换为URL安全格式的过程
            openFeignService.getProductByName(URLEncoder.encode("java-boy-pt","utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
