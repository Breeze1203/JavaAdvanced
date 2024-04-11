package com.example.mybatisplus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.io.Serial;
import java.io.Serializable;


@ToString
@AllArgsConstructor
@Data
@TableName(value = "user")
@NoArgsConstructor
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "username",jdbcType = JdbcType.VARCHAR)
    private String username;
    @TableField(value = "email",jdbcType = JdbcType.VARCHAR)
    private String email;
    @TableField(value = "rid",jdbcType = JdbcType.INTEGER)
    private Integer rid;
}
