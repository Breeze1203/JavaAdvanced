package org.pt.demo;


public interface animal {

     void run();


     private void call(String fullMessage) {
        System.out.println(fullMessage);
    }

    default void log(String msg){
         call(msg);
    }
}
