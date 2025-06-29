package org.pt.thread;

/**
 * @ClassName SynchronizedAllCases
 * @Author pt
 * @Description
 * @Date 2025/6/18 20:51
 **/
/**
 * 该类全面演示了synchronized关键字的所有使用情况。
 */
public class SynchronizedAllCases {

    private final Object instanceLock = new Object();


    // 锁对象：当前类的实例对象 (this)
    public synchronized void syncInstanceMethod() {
        System.out.println("Thread-" + Thread.currentThread().getId() + " 进入 syncInstanceMethod，锁是 [this] instance: " + this.hashCode());
        try {
            // 模拟业务耗时
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread-" + Thread.currentThread().getId() + " 离开 syncInstanceMethod");
    }

    // 锁对象：当前类的Class对象 (SynchronizedAllCases.class)
    public static synchronized void syncStaticMethod() {
        System.out.println("Thread-" + Thread.currentThread().getId() + " 进入 syncStaticMethod，锁是 [Class] object: " + SynchronizedAllCases.class.hashCode());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread-" + Thread.currentThread().getId() + " 离开 syncStaticMethod");
    }

    /**
     *
     * 锁对象：当前类的实例对象 (this)，效果等同于 Case 1
     */
    public void syncBlockOnThis() {
        synchronized (this) {
            System.out.println("Thread-" + Thread.currentThread().getId() + " 进入 syncBlockOnThis，锁是 [this] instance: " + this.hashCode());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread-" + Thread.currentThread().getId() + " 离开 syncBlockOnThis");
        }
    }

    /**
     *
     * 锁对象：成员变量 instanceLock
     */
    public void syncBlockOnObject() {
        synchronized (instanceLock) {
            System.out.println("Thread-" + Thread.currentThread().getId() + " 进入 syncBlockOnObject，锁是 [instanceLock] object: " + instanceLock.hashCode());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread-" + Thread.currentThread().getId() + " 离开 syncBlockOnObject");
        }
    }

    /**
     *
     * 锁对象：当前类的Class对象 (SynchronizedAllCases.class)，效果等同于 Case 2
     */
    public void syncBlockOnClass() {
        synchronized (SynchronizedAllCases.class) {
            System.out.println("Thread-" + Thread.currentThread().getId() + " 进入 syncBlockOnClass，锁是 [Class] object: " + SynchronizedAllCases.class.hashCode());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread-" + Thread.currentThread().getId() + " 离开 syncBlockOnClass");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SynchronizedAllCases demo1 = new SynchronizedAllCases();
        SynchronizedAllCases demo2 = new SynchronizedAllCases();

        System.out.println("======== 演示实例锁（同一个实例）========");
        // 两个线程竞争同一个实例(demo1)的锁，会互斥
        new Thread(demo1::syncInstanceMethod, "T1-A").start();
        new Thread(demo1::syncBlockOnThis, "T1-B").start();
        Thread.sleep(5000); // 等待上面执行完

        System.out.println("\n======== 演示实例锁（不同实例）========");
        // 两个线程分别作用于不同实例(demo1, demo2)，锁对象不同，不会互斥
        new Thread(demo1::syncInstanceMethod, "T2-A").start();
        new Thread(demo2::syncInstanceMethod, "T2-B").start();
        Thread.sleep(5000);

        System.out.println("\n======== 演示类锁（Class锁）========");
        // 两个线程竞争同一个Class锁，即使作用于不同实例，依然会互斥
        new Thread(demo1::syncBlockOnClass, "T3-A").start();
        new Thread(SynchronizedAllCases::syncStaticMethod, "T3-B").start();
        Thread.sleep(5000);

        System.out.println("\n======== 演示实例锁和类锁互不影响 ========");
        // 一个线程获取实例锁(demo1)，一个线程获取类锁，锁对象不同，不会互斥
        new Thread(demo1::syncInstanceMethod, "T4-A").start();
        new Thread(SynchronizedAllCases::syncStaticMethod, "T4-B").start();
        Thread.sleep(5000);

        System.out.println("\n======== 演示不同实例成员锁互不影响 ========");
        // 两个线程在同一个实例上，但锁的是不同的成员对象，不会互斥
        new Thread(demo1::syncBlockOnThis, "T5-A").start();
        new Thread(demo1::syncBlockOnObject, "T5-B").start();
    }
}
