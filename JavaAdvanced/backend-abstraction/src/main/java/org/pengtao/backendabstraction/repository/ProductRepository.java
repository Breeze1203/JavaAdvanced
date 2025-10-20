package org.pengtao.backendabstraction.repository;

import org.hibernate.annotations.SQLSelect;
import org.pengtao.backendabstraction.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory(String category);
}
