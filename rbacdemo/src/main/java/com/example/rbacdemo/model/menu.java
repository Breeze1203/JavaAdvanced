package com.example.rbacdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
public class menu {
    private int id;
    private String url;
    private String path;
    private String component;
    private String name;
    private int parentId;
    private List<menu> children;
}
