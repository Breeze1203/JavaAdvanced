package com.example.adminflow.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
public class Menu {
    private int id;
    private String url;
    private String path;
    private String component;
    private String name;
    private int parentId;
    private List<Menu> children;
}
