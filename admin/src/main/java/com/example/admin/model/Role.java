package com.example.admin.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Role {
    private int id;
    private String role_name;
    private String nameZh;
    private String create_time;
    private String description;
}
