package com.example.mybatisplus.lambda;

public class DoubleColon {
    public static void printStr(String str) {
        System.out.println("printStr : " + str);
    }

    public void toUpper() {
        System.out.println("toUpper: " + this.toString());
    }

    public void toLower(String str) {
        System.out.println("toLower: " + str);
    }

    public int toInt(String str) {
        System.out.println("toInt: " + str);
        return 1;
    }

    public void printInteger(Integer i) {
        System.out.println("printInteger: " + i);
    }
}
