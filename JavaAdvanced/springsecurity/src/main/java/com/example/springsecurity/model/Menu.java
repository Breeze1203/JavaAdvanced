package com.example.springsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Menu {
    private Integer menu_id;
    private String menu_name;
    private Integer parent_id;
    private String path;
    private List<Menu> menus;
}
