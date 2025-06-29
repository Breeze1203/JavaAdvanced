package org.pt.thread;

/**
 * @ClassName SyncExample
 * @Author pt
 * @Description
 * @Date 2025/6/18 20:41
 **/
public class SyncExample {
    private final Object lock = new Object();

    public void syncBlock() {
        synchronized (lock) {
            System.out.println("进入同步代码块");
        }
    }


    public synchronized void syncMethod() {
        System.out.println("进入同步方法");
    }
}
