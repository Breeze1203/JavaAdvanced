package com.example.rbacdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class user {
    private int id;
    private Long phone;
    private String address;
    private String username;
    private String password;

}
