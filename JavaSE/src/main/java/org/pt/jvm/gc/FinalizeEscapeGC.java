package org.pt.jvm.gc;

public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still alive :) ");
    }

    /*
    finalize()方法通常用于在对象被垃圾收集前进行清理工作
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize mehtod executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK=new FinalizeEscapeGC();
        // 对象第一次拯救自己
        SAVE_HOOK=null;
        System.gc();
        // 因为Finalize方法优先级很低，暂停0.5秒，以等待它
        Thread.sleep(500);
        if(SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no,i am dead");
        }
        SAVE_HOOK=null;
        System.gc();
        // 因为Finalize方法优先级很低，暂停0.5秒，以等待它
        Thread.sleep(500);
        if(SAVE_HOOK!=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no,i am dead");
        }
    }
}
