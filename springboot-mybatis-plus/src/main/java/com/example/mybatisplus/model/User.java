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
import java.util.Date;
import java.util.List;

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
    @TableField(value = "name",jdbcType = JdbcType.VARCHAR)
    private String name;
    @TableField(value = "age",jdbcType = JdbcType.INTEGER)
    private Integer age;
    @TableField(value = "date",jdbcType = JdbcType.DATE)
    private Date date;
    @TableField(value = "departmentId",jdbcType = JdbcType.INTEGER)
    private Integer departmentId;
}
