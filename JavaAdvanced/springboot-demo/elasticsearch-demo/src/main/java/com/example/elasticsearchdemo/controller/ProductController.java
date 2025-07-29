package com.example.elasticsearchdemo.controller;

/**
 * @ClassName ProductController
 * @Author pt
 * @Description
 * @Date 2025/7/12 21:33
 **/

import com.example.elasticsearchdemo.entity.Product;
import com.example.elasticsearchdemo.service.impl.ProductRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        // 调用 save 方法，即可将数据索引到 Elasticsearch
        return productRepository.save(product);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("query") String query) {
        // 调用我们自定义的查询方法
        return productRepository.findByNameOrDescription(query, query);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        // Spring Data ES 内置的 findById 方法
        return productRepository.findById(id)
                .orElse(null);
    }
}
