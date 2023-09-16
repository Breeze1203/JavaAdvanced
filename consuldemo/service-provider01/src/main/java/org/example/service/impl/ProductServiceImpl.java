package org.example.service.impl;


import org.example.pojo.Product;
import org.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> selectProductById(Integer id) {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1,"华为手机",1,5800D));
        products.add(new Product(2,"联想笔记本",1,6888D));
        products.add(new Product(3,"小米平板",5,2020D));
        return products;
    }
}
