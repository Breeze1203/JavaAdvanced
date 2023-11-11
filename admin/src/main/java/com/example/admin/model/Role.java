package com.example.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class Role {
    private int id;
    private String role_name;
    private String nameZh;
}
