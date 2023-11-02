package com.example.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
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
    private boolean state;
    private Integer organ;
    private Organization organization;
}
