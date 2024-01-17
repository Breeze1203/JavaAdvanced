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
public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;
    private Integer id;
    private String name;
    private Integer parentId;
    private String depPath;
    private List<Organization> children;
}
