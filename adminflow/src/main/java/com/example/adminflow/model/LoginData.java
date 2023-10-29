package com.example.adminflow.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class LoginData {
    private Integer id;
    private String login_user;
    private String operation_log;
    private String login_Date;
}
