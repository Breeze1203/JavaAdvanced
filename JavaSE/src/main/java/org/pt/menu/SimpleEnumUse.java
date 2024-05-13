package org.pt.menu;

public class SimpleEnumUse {
    public static void main(String[] args) {
        Spiciness howHot=Spiciness.HOT;
        System.out.println(howHot);
        for (Spiciness s:Spiciness.values()) {
            System.out.println(s);
        }
    }
}
