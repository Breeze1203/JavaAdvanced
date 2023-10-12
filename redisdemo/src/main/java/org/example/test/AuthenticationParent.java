package org.example.test;

public class AuthenticationParent implements Authentication{
    @Override
    public void success() {
        System.out.println("成功调用");
    }
}
