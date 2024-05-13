package org.pt.generic;

public class AoxImpl<T> implements Aox<T>{

    private T t;
    @Override
    public T getNumber(T t) {
        return t;
    }

    @Override
    public void get() {
        System.out.println(t);
    }

    public AoxImpl(T t) {
        this.t = t;
    }
}
