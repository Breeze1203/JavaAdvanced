package org.pt.thread;

/**
 * @ClassName ThreadLocalExample
 * @Author pt
 * @Description
 * @Date 2025/6/4 17:13
 **/
public class ThreadLocalExample {

    // 1. 创建一个 ThreadLocal 变量
    //    每个线程都会有这个  threadLocalValue 的独立副本
    private static final ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>() {
        @Override
        protected Integer initialValue() {
            // 2. 设置初始值（可选，如果不设置，默认为 null）
            //    当线程第一次调用 get() 方法时，如果没有先 set()，则会调用此方法
            return 0; // 每个线程的初始值都是 0
        }
    };

    public static void main(String[] args) {
        // 创建两个线程
        Thread thread1 = new Thread(new MyRunnable("Thread-1"));
        Thread thread2 = new Thread(new MyRunnable("Thread-2"));

        thread1.start();
        thread2.start();

        try {
            thread1.join(); // 等待 thread1 执行完毕
            thread2.join(); // 等待 thread2 执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 主线程尝试获取 ThreadLocal 值 (会是主线程的初始值，如果之前没 set 过)
        System.out.println(Thread.currentThread().getName() + " final ThreadLocal value: " + threadLocalValue.get());
        threadLocalValue.remove(); // 清理主线程的 ThreadLocal 值
    }

    static class MyRunnable implements Runnable {
        private String threadName;

        public MyRunnable(String name) {
            this.threadName = name;
        }

        @Override
        public void run() {
            // 3. 获取当前线程的 ThreadLocal 值
            System.out.println(threadName + " initial value: " + threadLocalValue.get());

            // 4. 设置当前线程的 ThreadLocal 值
            int newValue = (int) (Math.random() * 100);
            threadLocalValue.set(newValue);
            System.out.println(threadName + " set value to: " + newValue);

            // 再次获取，验证值已改变，并且是当前线程独有的
            System.out.println(threadName + " current value: " + threadLocalValue.get());

            // 模拟一些其他操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 再次获取，验证值仍然是之前设置的
            System.out.println(threadName + " final value before remove: " + threadLocalValue.get());

            // 5. 清理当前线程的 ThreadLocal 值 (非常重要，防止内存泄漏)
            //    特别是在使用线程池时，线程会被复用，如果不清理，下一个使用该线程的任务可能会拿到旧值。
            threadLocalValue.remove();
            System.out.println(threadName + " value after remove: " + threadLocalValue.get()); // 调用 initialValue() 或为 null
        }
    }
}
