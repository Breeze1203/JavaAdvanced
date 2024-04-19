package com.xxxx.proxy;

public class JdkHandlerTest {

    public static void main(String[] args) {

//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
//        // 目标对象
//        You you = new You();
//        // 得到代理类
//        JdkHandler jdkHandler = new JdkHandler(you);
//        // 得到代理对象
//        Marry marry = (Marry) jdkHandler.getProxy();
//        // 通过代理对象调用目标对象的方法
//        marry.toMarry();
//        String msg = marry.toMarry02();
//        System.out.println(msg);
        Owner owner = new Owner();
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(owner);
        // 注意这里应该是接口不是实现类
        RentHouse rentHouse= (RentHouse) jdkDynamicProxy.getProxy();
        rentHouse.toRentHouse();


       /* // 目标对象
        Owner owner = new Owner();
        // 得到目标对象的代理类
        JdkHandler jdkHandler02 = new JdkHandler(owner);
        // 得到代理对象
        RentHouse rentHouse = (RentHouse) jdkHandler02.getProxy();
        // 通过代理对象调用目标对象的方法
        rentHouse.toRentHouse();*/



    }
}
