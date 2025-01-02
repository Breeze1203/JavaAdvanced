package org.pt.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyThreadLocal
 * @Author pt
 * @Description
 * @Date 2024/12/5 14:57
 **/
public class MyThreadLocal {
    private static final ThreadLocal<String> threadLocalName = ThreadLocal.withInitial(() -> Thread.currentThread().getName());

    public static void main(String[] args) {
        List<String> list=new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("threadName: " + threadLocalName.get());
            }, "yes-thread-" + i).start();
        }
    }
}
