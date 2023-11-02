package com.example.admin.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Organization {
    private Integer id;
    private String name;
    private Integer parentId;
    private String depPath;
    private List<Organization> children;
}
