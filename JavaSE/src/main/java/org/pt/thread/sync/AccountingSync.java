package org.pt.thread.sync;

public class AccountingSync implements Runnable{
    static int i=0;
    public synchronized void increase(){
        i++;
    }
    @Override
    public void run() {
        for (int j = 0; j <1000000 ; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AccountingSync account = new AccountingSync();
        Thread thread1 = new Thread(account);
        Thread thread2 = new Thread(account);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("i:"+AccountingSync.i);
    }
}
