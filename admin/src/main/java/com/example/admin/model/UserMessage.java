package com.example.admin.model;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserMessage {
    private Integer id;
    private Integer uId; // 用户id
    private String mId; // 消息id
}
