package com.example.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Role {
    private int id;
    private String role_name;
    private String nameZh;
}
