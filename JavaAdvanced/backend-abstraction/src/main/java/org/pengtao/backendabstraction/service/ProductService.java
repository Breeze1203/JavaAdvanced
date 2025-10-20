package org.pengtao.backendabstraction.service;

import org.pengtao.backendabstraction.entity.ProductEntity;
import org.pengtao.backendabstraction.service.base.IBaseService;
import java.util.List;

public interface ProductService extends IBaseService<ProductEntity, Long> {
    List<ProductEntity> findByName(String name);
}