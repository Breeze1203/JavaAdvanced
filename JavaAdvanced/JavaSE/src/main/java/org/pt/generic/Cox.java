package org.pt.generic;

public class Cox <A,B,C>{
    private A a;
    private B b;
    private C c;

    public Cox(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
        System.out.println(a.getClass().getName());
        System.out.println(b.getClass().getName());
        System.out.println(c.getClass().getName());
    }
}
