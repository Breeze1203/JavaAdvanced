package org.example.service.impl;

import org.example.pojo.Product;
import org.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> setlectProductList() {
        return Arrays.asList(new Product(7072,"华为手机",1,5800D),
                new Product(2,"联想笔记本",1,6888D),
                new Product(3,"小米平板",5,2020D));
    }

    @Override
    public Map<Object, Object> createProduct(Product product) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("code",200);
        objectObjectHashMap.put("message","新增成功");
        return objectObjectHashMap;
    }
}
