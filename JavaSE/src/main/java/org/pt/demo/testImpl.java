package org.pt.demo;

/**
 * @ClassName testImpl
 * @Author pt
 * @Description
 * @Date 2024/12/18 13:52
 **/
public class testImpl implements test{
    @Override
    public void start() {
        System.out.println("start.....");
    }

    public static void main(String[] args) {
        test t=new testImpl();
        t.start();
    }
}
