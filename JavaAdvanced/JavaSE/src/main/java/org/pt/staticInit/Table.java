package org.pt.staticInit;

public class Table {
    static Bowl bowl =new Bowl(1);
    Table(){
        System.out.println("Table()");
        bowl.f1(1);
    }
    void f2(int marker){
        System.out.println("f2("+marker+")");
    }
    static Bowl bow2=new Bowl(2);
}
