package org.pt.staticInit;

public class Cupboard {
    Bowl bow3=new Bowl(3);
    static Bowl bow4=new Bowl(4);
    Cupboard(){
        System.out.println("Cupboard()");
        bow4.f1(12);
    }
    void f3(int marker){
        System.out.println("f3("+marker+")");
    }

    static Bowl bow5=new Bowl(5);

    static Table table=new Table();
    static Cupboard cupboard=new Cupboard();

    public static void main(String[] args) {
        System.out.println("create");
        new Cupboard();
        System.out.println("create");
        new Cupboard();
    }
    /*
    结论：当一个类有静态对象时候，先去创建静态对象，然后在进行new对象，调用构造方法
    可以看到：执行顺序为
     bow4->bowl构造方法->bow5->bowl构造方法->table->bow1->bow2->table构造方法
     ->cupboard->bow3->cupboard构造方法
     可以看到静态对象，只会创建一次，并且最先创建
     */
}
