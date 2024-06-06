package org.pt.design.chaindesign;

public abstract class Middleware {
    private Middleware next;
    public static Middleware link(Middleware first,Middleware... chain){
        Middleware head=first;
        for (Middleware nextInChain:chain){
            head.next=nextInChain;
            head=nextInChain;
        }
        return first;
    }

    public abstract boolean check(String email,String password,String phone);

    protected boolean checkNext(String email,String password,String phone){
        if (next==null){
            System.out.println("校验通过");
            return true;
        }
        return next.check(email,password,phone);
    }
}
