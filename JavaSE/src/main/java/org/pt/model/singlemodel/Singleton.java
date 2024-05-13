package org.pt.model.singlemodel;

public final class Singleton {
    private static Singleton singleton;
    private String value;

    private Singleton(String value){
        this.value=value;
    }

    public static Singleton getSingleton(String value){
        if(singleton==null){
            singleton=new Singleton(value);
        }
        return singleton;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
