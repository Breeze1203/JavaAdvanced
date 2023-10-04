package org.example.service;

import org.example.pojo.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestMapping("/hystrix") // 防止地址重复
public class HystrixService implements OpenFeignService{
    @Override
    public List<Product> getProduct() {
        List<Product> products=new ArrayList<>();
        return null;
    }

    @Override
    public String hello(String name) {
        return "error";
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProductById(Integer id) {

    }

    @Override
    public void getProductByName(String name) {

    }
}
