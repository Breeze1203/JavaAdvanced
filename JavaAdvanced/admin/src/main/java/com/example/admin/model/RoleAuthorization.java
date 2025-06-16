package com.example.admin.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
public class RoleAuthorization implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;
    private Integer id;
    private Integer rId;
    private Integer aId;
}
