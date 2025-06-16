package com.pt.mvc.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    String name();

    boolean required() default true;

    String defaultValue() default "";

}
