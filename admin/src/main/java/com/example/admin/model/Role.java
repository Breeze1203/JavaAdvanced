package com.example.admin.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;
    private int id;
    private String role_name;
    private String nameZh;
    private String create_time;
    private String description;
}
