package org.pt.generic;

public class GenericMethods {
    public static <T> void f(T t){
        System.out.println(t.getClass().getName());
    }

    public static void main(String[] args) {
        f("a");
        f(1);;
        f(1.0);
        f(1.0F);
        f('c');
    }
}
