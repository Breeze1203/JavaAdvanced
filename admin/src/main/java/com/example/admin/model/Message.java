package com.example.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;
    private Integer id;
    private Integer send_id;
    private Integer receive_id;
    private String content;
    private String time;
    private Boolean state;
    private User send_user;
    private User receive_user;
}
