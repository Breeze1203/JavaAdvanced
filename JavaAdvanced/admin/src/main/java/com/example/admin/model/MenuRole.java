package com.example.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/*
用户角色关联表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuRole {
    @Serial
    private static final long serialVersionUID=1L;
    /*
    用户角色关联表主键
     */
    private Integer id;
    /*
    角色id
     */
    private Integer rId;
    /*
    菜单id
     */
    private Integer mId;
}
