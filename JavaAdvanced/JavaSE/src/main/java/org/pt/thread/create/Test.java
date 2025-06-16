package org.pt.thread.create;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        /*方案一 继承Thread
        ThreadDemo01 threadDemo01 = new ThreadDemo01();
        ThreadDemo01 threadDemo02 = new ThreadDemo01();
        ThreadDemo01 threadDemo03 = new ThreadDemo01();
        threadDemo01.setName("刘备");
        threadDemo02.setName("关羽");
        threadDemo03.setName("张飞");
        threadDemo01.start();
        threadDemo02.start();
        threadDemo03.start();


        //实现Runnable接口
        MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable,"张飞");
        Thread thread2 = new Thread(myRunnable,"貂蝉");
        Thread thread3 = new Thread(myRunnable,"刘备");
        thread1.start();
        thread2.start();
        thread3.start();

        //创建异步任务
        /*
        FutureTask<String> task=new FutureTask<String>(new CallerTask());
        //启动线程

        new Thread(task).start();
        try {
            //等待执行完成，并获取返回结果
            String result=task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */
        MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable,"张飞");
        Thread thread2 = new Thread(myRunnable,"貂蝉");
        Thread thread3 = new Thread(myRunnable,"刘备");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
