package com.example.rbacdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
该类是用户可访问menu和角色的权限关联表
 */
@Data
@NoArgsConstructor
public class mrole {
    private int id;
    private int menu_id;
    private int role_id;
}
