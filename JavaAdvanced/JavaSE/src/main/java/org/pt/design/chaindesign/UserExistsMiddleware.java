package org.pt.design.chaindesign;

/*
用户是否存在校验
 */
public class UserExistsMiddleware extends Middleware {
    private Server server;

    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    public boolean check(String email, String password,String phone) {
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return checkNext(email, password,phone);
        } else {
            System.out.println("This email is registered!");
            return false;
        }

    }
}
