package org.pt.initialization;

public class House {
    Window w=new Window(0);
    Window w1=new Window(1);
    House(){
       w3=new Window(33);
    }
    Window w2=new Window(2);
    void f(){
        System.out.println("f()");
    }

    Window w3=new Window(3);

    public static void main(String[] args) {
        House house = new House();
        house.f();
    }

    /*
    输出
    window(0)
window(1)
window(2)
window(3)
window(33)
f()

原因：变量定义的先后顺序决定了初始化顺序，即时散布于方法定义之间
仍旧会在任何方法之前被调用
     */
}
