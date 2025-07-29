package com.example.elasticsearchdemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @ClassName Product
 * @Author pt
 * @Description
 * @Date 2025/7/12 21:29
 **/
@Data
@Document(indexName = "products")
public class Product {

    @Id // 标记这是文档的唯一标识
    private String id;

    // 字段注解：name 字段为 Text 类型，会进行分词，适合全文搜索
    @Field(type = FieldType.Text)
    private String name;

    // description 字段也配置为可分词的 Text 类型
    @Field(type = FieldType.Text)
    private String description;

    // price 字段为 Double 类型，不会被分词
    @Field(type = FieldType.Double)
    private Double price;
}
