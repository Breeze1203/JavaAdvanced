package com.example.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
该类是用户和角色的权限关联表
 */

@Data
@NoArgsConstructor
public class Urole {
    private int id;
    private int user_id;
    private int role_id;
}
