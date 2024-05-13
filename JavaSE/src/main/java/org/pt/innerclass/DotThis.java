package org.pt.innerclass;

/*
需要生成外部类对象的引用，使用外部类的名字紧跟原点和this
 */
public class DotThis {
    void f(){
        System.out.println("DotThis.f()");
    }

    public class Inner{
        public DotThis outer(){
            return DotThis.this;
        }
    }
    public Inner inner(){
        return new Inner();
    }

    public static void main(String[] args) {
        DotThis dt = new DotThis();
        DotThis.Inner dt1 = dt.inner();
        dt1.outer().f();
        // 创建内部类对象 必须这样
        Inner inner = dt.new Inner();

    }
}
