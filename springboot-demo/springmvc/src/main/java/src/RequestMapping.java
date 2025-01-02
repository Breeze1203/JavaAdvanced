package src;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 声明注解的生命周期：RUNTIME表示运行时期有效
@Retention(RetentionPolicy.RUNTIME)
// 注解的生效范围：可应用在类上面、方法上面
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequestMapping {
    // 允许该注解可以填String类型的参数，默认为空
    String value() default "";
}