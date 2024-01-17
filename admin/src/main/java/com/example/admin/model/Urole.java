package com.example.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/*
该类是用户和角色的权限关联表
 */

@Data
@NoArgsConstructor
public class Urole implements Serializable {
    @Serial
    private static final long serialVersionUID= 1L;
    private int id;
    private int user_id;
    private int role_id;
}
