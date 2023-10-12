package org.example.test;

public class Parent {

    public void test(Authentication authentication){
        authentication.success();
    }

    public static void main(String[] args) {
        Parent parent = new Parent();
        parent.test(new AuthenticationParent());
    }
}
