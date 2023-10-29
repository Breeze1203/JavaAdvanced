package com.example.adminflow.util;


import com.example.adminflow.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class StatusUtil {
    public String message;
    public Integer code;
    public User user;
}
