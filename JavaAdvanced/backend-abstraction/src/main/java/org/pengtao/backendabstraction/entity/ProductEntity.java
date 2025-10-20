package org.pengtao.backendabstraction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.pengtao.backendabstraction.entity.base.BaseEntity;

@Entity
@Table(name = "tb_products")
public class ProductEntity extends BaseEntity {

    private String name;
    private Double price;
}
