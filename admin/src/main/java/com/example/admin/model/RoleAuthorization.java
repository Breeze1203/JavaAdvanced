package com.example.admin.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleAuthorization {
    private Integer id;
    private Integer rId;
    private Integer aId;
}
