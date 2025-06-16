package org.pt.thread.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;
    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<Thread>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        cas.count();
                        cas.safeCount();
                    }
                }
            });
            ts.add(t);
        }

        for (Thread t : ts) {
            t.start();
        }
        // 等待所有线程执行完成
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cas.i);
        System.out.println(cas.atomicI.get());
        System.out.println(System.currentTimeMillis() - start);
    }


    /*** 使用CAS实现线程安全计数器 */

//    for (;;)：这是一个无限循环，意味着循环会一直执行，直到遇到break语句。
//    int i = atomicI.get();：获取atomicI当前的值，并将其存储在局部变量i中。
//    boolean suc = atomicI.compareAndSet(i, ++i);：调用compareAndSet方法尝试将atomicI的值从i更新为i+1。这个方法会原子地检查atomicI的当前值是否等于传入的第一个参数i。
//    如果相等，它会将值更新为第二个参数++i，并返回true表示更新成功；如果不相等，它会返回false，表示更新失败。
//    if (suc) { break; }：如果compareAndSet方法返回true，表示更新成功，那么执行break语句，跳出循环
    private void safeCount() {
        for (;;) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    /*** 非线程安全计数器*/
    private void count() {
        i++;
    }
}
