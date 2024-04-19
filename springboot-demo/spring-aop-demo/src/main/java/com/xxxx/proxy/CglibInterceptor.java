package com.xxxx.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibInterceptor implements MethodInterceptor {

    // 目标对象
    private Object target;
    // 通过构造器传入目标对象
    public CglibInterceptor(Object target) {
        this.target = target;
    }

    /**
     * 获取代理对象
     * @return
     */
    public Object getProxy(){
        // 通过Enhancer对象中的create()方法生成一个类，用于生成代理对象
        Enhancer enhancer = new Enhancer();
        // 设置父类 （将目标类作为代理类的父类）
        enhancer.setSuperclass(target.getClass());
        // 设置拦截器 回调对象为本身对象
        enhancer.setCallback(this);
        // 生成代理类对象，并返回给调用者
        return enhancer.create();
    }


    /**
     * 拦截器
     *    1. 目标对象的方法调用
     *    2. 行为增强
     * @param o cglib动态生成的代理类的实例
     * @param method    实体类所调用的都被代理的方法的引用
     * @param objects   参数列表
     * @param methodProxy   生成的代理类对方法的代理引用
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        // 增强行为
        System.out.println("===================方法前执行");

        // 调用目标类中的方法
        Object object = methodProxy.invoke(target, objects);

        // 增强行为
        System.out.println("方法后执行===================");

        return object;
    }


}
