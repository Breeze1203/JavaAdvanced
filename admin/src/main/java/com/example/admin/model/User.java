package com.example.admin.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
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
