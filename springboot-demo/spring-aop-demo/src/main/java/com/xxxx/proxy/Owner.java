package com.xxxx.proxy;

/**
 * 静态代理 -> 目标对象
 *      实现行为
 */
public class Owner implements RentHouse {
    @Override
    public void toRentHouse() {
        System.out.println("两室一厅，月租五千！");
    }

    @Override
    public void pay() {
        System.out.println("付款了");
    }
}
