package com.example.adminflow.util;


import com.example.adminflow.model.User;
import lombok.Data;

@Data
public class StatusUtil {

    private String  message;


    private User u;

    public StatusUtil(String message, User u) {
        this.message = message;
        this.u = u;
    }
}
