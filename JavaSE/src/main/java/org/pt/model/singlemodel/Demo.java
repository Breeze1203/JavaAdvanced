package org.pt.model.singlemodel;

public class Demo {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getSingleton("创建一个对象");
        Singleton s2 = Singleton.getSingleton("创建两个对象");
        System.out.println(s1.getValue());
        System.out.println(s2.getValue());
    }
}
