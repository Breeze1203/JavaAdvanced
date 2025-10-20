package org.pengtao.backendabstraction.controller;

import org.pengtao.backendabstraction.common.ApiResponse;
import org.pengtao.backendabstraction.controller.base.BaseController;
import org.pengtao.backendabstraction.entity.ProductEntity;
import org.pengtao.backendabstraction.service.ProductService;
import org.pengtao.backendabstraction.service.base.IBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController<ProductEntity,Long> {

    private final ProductService productService; // 保留引用, 以备调用特有方法

    public ProductController(ProductService productService) {
        super(productService); // 把 service 传给父类
        this.productService = productService;
    }

    // 所有 CRUD (POST, GET, PUT, DELETE) 路由已经自动拥有了

    // 在这里添加特有的路由
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductEntity>>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success(productService.findByName(name)));
    }
}
