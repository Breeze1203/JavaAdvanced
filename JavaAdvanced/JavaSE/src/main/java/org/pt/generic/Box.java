package org.pt.generic;
/*
泛型类
 */
public class Box <T,C> extends Cox{
    public Box(T t,C c) {
        super(1,'c',new GenericMethods());
        this.t = t;
        this.c = c;
        System.out.println(t.getClass().getName());
        System.out.println(c.getClass().getName());
    }

    private T t;
    private C c;

}
