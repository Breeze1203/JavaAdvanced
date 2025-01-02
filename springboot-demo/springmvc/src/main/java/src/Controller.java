package src;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 声明注解的生命周期：RUNTIME表示运行时期有效
@Retention(RetentionPolicy.RUNTIME)
// 注解的生效范围：只能生效于类上面
@Target(ElementType.TYPE)
public @interface Controller {
    //@interface是元注解：JDK封装的专门用来实现自定义注解的注解
}
