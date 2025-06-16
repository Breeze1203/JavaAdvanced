package org.pt.model.factorymodel;

public class Demo {
    private static Factory factory;
    public static void main(String[] args) {
        createButton();
        paint();
    }

    static Factory createButton(){
        if (System.getProperty("os.name").equals("Windows 10")) {
            factory = new WindowButtonFactory();
        } else {
            factory=new MacButtonFactory();
        }
        return factory;
    }

    static void paint(){
        factory.paint();
    }
}
