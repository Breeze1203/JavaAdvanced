package org.pt.array;

public class VarargType {
    static void f(String... args) {
        for (String a : args) {
            System.out.println(a);
        }
    }
    static void g(Integer... args) {
        for (Integer a : args) {
            System.out.println(a);
        }
    }

    public static void main(String[] args) {
        f();
        f("a","b","c");
        g();
        g(1,2,4);
    }
}
