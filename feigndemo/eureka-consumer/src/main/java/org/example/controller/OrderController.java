package org.example.controller;

import org.example.pojo.Order;
import org.example.pojo.Product;
import org.example.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;



@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/{id}")
    private Order selectOrderById(@PathVariable("id")Integer id){
        return orderService.selectOrderById(id);
    }

    @PostMapping("/save")
    private Map<Object,Object> createProduct(Product product){
        return orderService.createProduct(product);
    }
}
