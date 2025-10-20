package org.pengtao.backendabstraction.service.impl;

import org.pengtao.backendabstraction.entity.ProductEntity;
import org.pengtao.backendabstraction.repository.ProductRepository;
import org.pengtao.backendabstraction.service.ProductService;
import org.pengtao.backendabstraction.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductEntity, Long> implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findByName(String name) {
        return List.of();
    }
}
