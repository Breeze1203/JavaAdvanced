package org.example.service.impl;

import org.example.pojo.Product;
import org.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> getById(int id) {
        ArrayList<Product> products = new ArrayList<>();
        Product product1= new Product("华为", 6000, 21);
        Product product2= new Product("三星", 7000, 11);
        Product product3= new Product("苹果", 16000, 7);
        products.add(product1);
        products.add(product2);
        products.add(product3);
        return products;
    }
}
