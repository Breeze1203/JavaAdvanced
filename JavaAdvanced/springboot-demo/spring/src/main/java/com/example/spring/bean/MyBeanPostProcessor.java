package com.example.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyBeanPostProcessor
 * @Author pt
 * @Description
 * @Date 2025/6/18 12:10
 **/
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 这里就是判断逻辑，只有当是 MySpringBean 类型的 Bean 时，才打印信息
        if (bean instanceof MySpringBean) {
            System.out.println("6. BeanPostProcessor.postProcessBeforeInitialization() 调用：Bean '" + beanName + "'。");
        }
        return bean; // 总是返回 bean 实例
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 同样，这里判断是否为 MySpringBean
        if (bean instanceof MySpringBean) {
            System.out.println("8. BeanPostProcessor.postProcessAfterInitialization() 调用：Bean '" + beanName + "'。");
        }
        return bean; // 总是返回 bean 实例
    }
}
