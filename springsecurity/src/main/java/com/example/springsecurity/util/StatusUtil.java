package com.example.springsecurity.util;

import com.example.springsecurity.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusUtil {
    private String message;
    private Integer code;
    private User user;
}
