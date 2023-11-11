package com.example.admin.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
该注解是修改权限的注解
 */
@Target({ElementType.METHOD,ElementType.TYPE}) // 指定注解应用于哪些元素(类，方法）
@Retention(RetentionPolicy.RUNTIME) //表示注解在源代码、字节码和运行时环境中都可见，可以通过反射获取
public @interface CheckPermission {
    String permission();
}
