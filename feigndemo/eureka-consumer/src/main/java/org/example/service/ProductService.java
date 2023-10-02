package org.example.service;


import org.example.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient("service-provider")
public interface ProductService {
    @GetMapping("/product/list")
    List<Product> setlectProductList();

    @PostMapping("/product/save")
    Map<Object,Object> createProduct(Product product);

}
