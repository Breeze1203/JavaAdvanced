package org.pt.thread.sync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName SyncDemo
 * @Author pt
 * @Description
 * @Date 2024/12/5 14:39
 **/
public class SyncDemo {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int i;

    public synchronized void add(){
        i++;
    }

    public static void main(String[] args) {

    }
}
