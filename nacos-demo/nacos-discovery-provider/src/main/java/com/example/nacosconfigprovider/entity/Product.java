package com.example.nacosconfigprovider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private Integer price;
    private Integer num;
}
