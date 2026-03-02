package org.pt.thread;


import java.util.LinkedList;

/**
 * @ClassName DeadTest
 * @Author pt
 * @Description
 * 锁的持有情况：在 pop() 方法中，你同时获取了两把锁：第一把是 this（synchronized 方法锁），第二把是 list 对象。
 * wait() 的机制：当你执行 wait() 时，它只会释放 this 对象的锁，而不会释放 list 对象的锁。
 * 死锁链条：
 * 线程 A 调用 pop()，获得 this 锁，接着获得 list 锁。
 * list 为空，线程 A 执行 wait()，释放了 this 锁，但依然拿着 list 锁
 * 线程 B 调用 push()，它需要先获得 this 锁（可以获得，因为 A 释放了），然后尝试获取 list 锁。
 * 死锁发生：线程 B 因为拿不到 list 锁（被 A 占着）而阻塞；线程 A 因为 B 无法执行 push 也就永远不会被 notify 唤醒
 * @Date 2026/3/2 12:17
 **/
public class DeadTest {
    public static void main(String[] args) {

    }

    public LinkedList list;
    public synchronized void push(Object x){
        synchronized (list){
            list.add(x);
            notify();
        }
    }

    public synchronized Object pop() throws InterruptedException {
        synchronized (list) {
            if (list.size() <= 0) {
                wait();
            }
        }
        return list.removeLast();
    }
}
