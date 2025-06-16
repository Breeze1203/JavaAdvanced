package com.xxxx.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理类
 *      每一个代理类都需要实现InvocationHandler接口
 */
public class JdkHandler implements InvocationHandler {

    // 目标对象
    private Object target; // 目标对象的类型不固定，创建时动态生成
    // 通过带参构造器传递目标对象
    public JdkHandler(Object target) {
        this.target = target;
    }


    /**
     * 1. 调用目标对象的方法 （返回Object）
     * 2. 增强目标对象的行为
     * @param proxy 调用该方法的代理实例
     * @param method 目标对象的方法
     * @param args 目标对象的方法所需要的的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("invoke方法的proxy参数：" + proxy.getClass().getName());

        // 用户的增强行为
        System.out.println("===============方法执行前");

        // 调用目标对象中的方法 （返回Object）
        Object object = method.invoke(target,args);

        // 用户的增强行为
        System.out.println("方法执行后===============");

        return object;
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
     *
     * @return
     */
    public Object getProxy(){
        Object object = Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
        System.out.println("getProxy返回的代理对象：" + object.getClass().getName());
        return  object;
    }


}
