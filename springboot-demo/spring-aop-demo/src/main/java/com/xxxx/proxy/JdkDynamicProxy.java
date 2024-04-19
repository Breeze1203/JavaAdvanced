package com.xxxx.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicProxy implements InvocationHandler {
    // 目标对象
    private Object target;

    public JdkDynamicProxy(Object target) {
        this.target = target;
    }

    // 调用目标对象中的方法 （返回Object）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("新娘是....");
        Object invoke = method.invoke(target,args);
        return invoke;
    }

    /**
     * 获取代理对象
     *  public static Object newProxyInstance(ClassLoader loader,
     *                                       Class[] interfaces,
     *                                       InvocationHandler h)
     *   loader：类加载器
     *   interfaces：接口数组
     *          target.getClass().getInterfaces()：目标对象的接口数组
     *   h：InvocationHandler接口（传入InvocationHandler接口的实现类）
     *   Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(),new Class );
     *
     * @return
     */
    // 获取代理对象
    public Object getProxy(){
        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(),this );
        return o;
    }
}
