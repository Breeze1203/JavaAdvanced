package com.example.admin.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;

@Data
@ToString
public class Authorization {
    @Serial
    private static final long serialVersionUID=1L;
    private Integer id;
    private String name;
    private String describe;
}
