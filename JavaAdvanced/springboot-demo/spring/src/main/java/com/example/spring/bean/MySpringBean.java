package com.example.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @ClassName MySpringBean
 * @Author pt
 * @Description
 * @Date 2025/6/18 11:49
 **/
@Service
public class MySpringBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("4. BeanFactoryAware.setBeanFactory() 调用：获取到 BeanFactory。");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("3. BeanNameAware.setBeanName() 调用：Bean 名称是 '" + name + "'。");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("bean实例销毁");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("7. 属性填充完毕");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("5. ApplicationContextAware.setApplicationContext() 调用：获取到 ApplicationContext。");
    }

    public MySpringBean() {
        System.out.println("\n--- MySpringBean 生命周期阶段 ---");
        System.out.println("1. 构造函数调用：Bean 已【实例化】。");
    }

    public void setMessage(String message) {
        System.out.println("2. 属性填充：设置 message = '" + message + "'。");
    }

}
