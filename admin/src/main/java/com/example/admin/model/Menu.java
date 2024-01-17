package com.example.admin.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {
    @Serial
    private static final long serialVersionUID=1L;
    private Integer menu_id;
    private String menu_name;
    private Integer parent_id;
    private String path;
    private String menu_role;
    private String icon;
    private List<Menu> menus;
}
