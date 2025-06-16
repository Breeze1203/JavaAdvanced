package com.example.admin.util;

import lombok.Data;
import lombok.ToString;

/*
该类是用来根据用户角色修改权限，前端传过来参数的工具类
 */

@Data
@ToString
public class Permission {
    private Integer rid;
    private Integer[] allId;
}
