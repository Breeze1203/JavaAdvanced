package com.example.elasticsearchdemo.service.impl;

/**
 * @ClassName ProductRepository
 * @Author pt
 * @Description
 * @Date 2025/7/12 21:31
 **/

import com.example.elasticsearchdemo.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    /**
     * @param name 匹配 name 字段的关键词
     * @param description 匹配 description 字段的关键词
     * @return 匹配到的商品列表
     */
    List<Product> findByNameOrDescription(String name, String description);

}
