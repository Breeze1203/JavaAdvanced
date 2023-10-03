package org.example.controller;

import org.example.pojo.Product;
import org.example.service.OpenFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/openfeign")
public class OpenfeignController {
    @Autowired
    OpenFeignService openFeignService;

    @GetMapping("/")
    public List<Product> getPro(){
        return openFeignService.getProduct();
    }
}
