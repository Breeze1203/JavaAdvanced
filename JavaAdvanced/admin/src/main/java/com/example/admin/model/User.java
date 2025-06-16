package com.example.admin.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;
    private Integer id;
    private Long phone;
    private String address;
    private String username;
    private String password;
    private String userFace;
    private String embod;
    private String email;
    private Boolean state;
    private Integer organizationId;
    private Organization organization;
    private Role role;
}
