package org.pt.demo;

/**
 * @ClassName cat
 * @Author pt
 * @Description
 * @Date 2025/6/25 10:39
 **/
public class cat implements animal{

    private animal a;

    @Override
    public void run() {
        System.out.println("cats run...");
    }

    public void test(animal a){
        a.log("测试...");
    }

    public static void main(String[] args) {
        dog dog = new dog();
        cat cat = new cat();
        cat.log("测试");
        cat.test(dog);
    }
}
