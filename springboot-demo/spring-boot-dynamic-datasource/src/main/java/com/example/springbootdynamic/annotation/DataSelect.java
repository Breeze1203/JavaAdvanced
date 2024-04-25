package com.example.springbootdynamic.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSelect {
    // 默认数据源
    String value() default "master";
}
