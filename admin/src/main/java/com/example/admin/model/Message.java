package com.example.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Message {
    private Integer id;
    private Integer send_id;
    private Integer receive_id;
    private String content;
    private String time;
    private Boolean state;
    private User send_user;
    private User receive_user;
}
