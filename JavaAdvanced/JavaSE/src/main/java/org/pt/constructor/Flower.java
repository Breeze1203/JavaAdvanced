package org.pt.constructor;

/*
知识点：一个构造器无法调用两个
除构造器外，禁止其它任何方法调用构造器
 */
public class Flower {
    int petalCount=0;
    String s="init value";
    Flower(String ss){
        System.out.println("String constructor");
        System.out.println("ss"+ss);
        s=ss;
    }
    Flower(String s,int petals){
        /*
        调用了
        Flower(int petals){
            petalCount=petals;
        }
         */
        // 调用别的构造器，必须放在第一行
        this(petals);
    }

    Flower(int petals){
        System.out.println("int constructor");
        petalCount=petals;
        System.out.println("petalCount"+petalCount);
    }

    Flower(){
        this("hi",47);
        System.out.println("no arg constructor");
    }

    void print(){
        System.out.println(this.petalCount);
        System.out.println(s);
    }

    public static void main(String[] args) {
        Flower flower = new Flower();
        flower.print();
    }

    /*
    输出:
    int constructor
    petalCount47
    no arg constructor
    47
    init value
     */

}
