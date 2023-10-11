package com.example.rbacdemo.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusUtil {
    private String  status_message;

    private Integer status_code;
}
