package com.example.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserMessage implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;
    private Integer id;
    private Integer uId; // 用户id
    private String mId; // 消息id
}
