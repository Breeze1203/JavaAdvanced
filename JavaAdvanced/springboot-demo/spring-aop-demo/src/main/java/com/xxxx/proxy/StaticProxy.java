package com.xxxx.proxy;

public class StaticProxy {

    public static void main(String[] args) {
        // 目标对象
        You you = new You();
        // 代理对象
        MarryCompanyProxy marryCompanyProxy = new MarryCompanyProxy(you);
        // 通过代理对象调用目标对象中的方法
        marryCompanyProxy.toMarry();

        // 目标对象
        Owner owner = new Owner();
        // 代理对象
        AgentProxy agentProxy = new AgentProxy(owner);
        // 通过代理对象调用目标对象中的方法
        agentProxy.toRentHouse();
    }


}
