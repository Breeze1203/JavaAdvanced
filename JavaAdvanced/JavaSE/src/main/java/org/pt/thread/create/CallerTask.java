package org.pt.thread.create;

import java.util.concurrent.Callable;

public class CallerTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "Hello,i am running!";
    }
}
