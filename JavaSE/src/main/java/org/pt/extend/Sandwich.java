package org.pt.extend;

public class Sandwich extends PortableLunch{
    private Bread b=new Bread();
    private Chess c=new Chess();
    private Lettuce l=new Lettuce();
    public Sandwich(){
        System.out.println("Sandwich()");
    }

    public static void main(String[] args) {
        new Sandwich();
    }

    /*
    存在多层继承时候，最新执行的是基类的构造器
    Meal->Lunch->PortableLunch->自己的构造器->成员变量初始化
     */
}
