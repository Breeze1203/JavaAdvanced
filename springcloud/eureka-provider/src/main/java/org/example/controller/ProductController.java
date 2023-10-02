package org.example;

import org.example.pojo.Product;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product/list")
public class ProductController {
    @Resource
    ProductService productService;
    public List<Product> getProductList(){
        return productService.getById(1);
    }
}
