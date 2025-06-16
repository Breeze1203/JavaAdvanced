package org.pt.design.chaindesign;

public class PhoneCheckMiddleware extends Middleware{
    public boolean check(String email, String password,String phone) {
        if (email.equals("123456")) {
            System.out.println(email+"登录成功");
            return true;
        }
        System.out.println("Hello, user!");
        return checkNext(email, password,password);
    }
}
