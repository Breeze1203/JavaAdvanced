package com.xxxx.proxy;

public class CglibInterceptorTest {

    public static void main(String[] args) {

        /*// 得到目标对象
        You you = new You();
        // 得到拦截器
        CglibInterceptor cglibInterceptor = new CglibInterceptor(you);
        // 得到代理对象
        Marry marry  = (Marry) cglibInterceptor.getProxy();
        // 通过代理对象调用目标对象中的方法
        marry.toMarry();*/


        /*通过Cglib动态代理实现：没有接口实现的类*/
       /* User user = new User();
        CglibInterceptor cglibInterceptor02 = new CglibInterceptor(user);
        User u = (User) cglibInterceptor02.getProxy();
        u.test();*/


        /*通过JDK动态代理实现：没有接口实现的类*/
       /* User user = new User();
        JdkHandler jdkHandler = new JdkHandler(user);
        User u = (User) jdkHandler.getProxy();
        u.test(); // 报错：com.sun.proxy.$Proxy0 cannot be cast to com.xxxx.proxy.User*/

    }
}
