package org.pt.generic;

public class GenericClass {
    public static void main(String[] args) {
        Box<String, Integer> b1 = new Box<String, Integer>("ccc",2);
        Box<GenericMethods, Character> b2 = new Box<>(new GenericMethods(), 'c');
        AoxImpl<Integer> integerAox = new AoxImpl<>(2);
        integerAox.get();
    }
}
