package org.pt.exception;

public class SimpleException extends Exception{
    public static void main(String[] args) {
        int test = test();
        System.out.println(test);
    }

    public static int test(){
        int a=10;
        int b=0;
        try {
            b=10/3;
            System.out.println("执行try");
        }catch (Exception e){
            System.out.println("执行catch");
            b=11;
            return b;
        }finally {
            b=12;
            System.out.println("执行finally");
            return b;
        }
    }
}
