package org.pt.thread.concurrent;

public class NonAtomicCounter {
    private int count = 0;

    public void increment() {
        synchronized (this){
            count++;
        }
    }

    public void decrement() {
        synchronized (this){
            count--;
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        NonAtomicCounter counter = new NonAtomicCounter();

        // 创建并启动一个线程，执行加一操作
        Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });

        // 创建并启动另一个线程，执行减一操作
        Thread decrementThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.decrement();
            }
        });

        incrementThread.start();
        decrementThread.start();

        try {
            // 等待两个线程完成
            incrementThread.join();
            decrementThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打印最终的计数
        System.out.println("Final count: " + counter.getCount());
    }
}