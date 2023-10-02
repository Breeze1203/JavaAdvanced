package org.example.service;

import org.example.pojo.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Product> getById(int id);
}
