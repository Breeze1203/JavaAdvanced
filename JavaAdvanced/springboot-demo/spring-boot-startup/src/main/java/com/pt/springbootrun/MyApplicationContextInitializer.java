package com.pt.springbootrun;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName MyApplicationContextInitializer
 * @Author pt
 * @Description
 * @Date 2025/7/4 20:47
 **/
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("====== [Initializer] " +
                "MyApplicationContextInitializer.initialize() 执行 ======");
        System.out.println("====== [Initializer] " +
                "此时可以对ApplicationContext进行一些自定义操作，Bean尚未加载。");
    }
}
