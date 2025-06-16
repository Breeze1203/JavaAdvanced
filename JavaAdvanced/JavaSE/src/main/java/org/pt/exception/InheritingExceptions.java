package org.pt.exception;

public class InheritingExceptions {
    public void f() throws SimpleException {
        System.out.println("throw SimpleException");
        throw new SimpleException();
    }

    public static void main(String[] args) {
        InheritingExceptions inheritingExceptions = new InheritingExceptions();
        try {
            inheritingExceptions.f();
        } catch (SimpleException e) {
            // 打印从方法调用处直到异常处
            e.printStackTrace();
            System.out.println("SimpleExceptio");
        }
    }
}
