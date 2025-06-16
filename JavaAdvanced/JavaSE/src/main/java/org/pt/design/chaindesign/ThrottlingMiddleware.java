package org.pt.design.chaindesign;

/*
访问接口时长校验
 */
public class ThrottlingMiddleware extends Middleware{

    private int requestPerMinute;
    private int request;
    private long currentTime;
    @Override
    public boolean check(String email, String password,String phone) {
        if (System.currentTimeMillis() > currentTime + 60_000) {
            request = 0;
            currentTime = System.currentTimeMillis();
        }
        request++;
        if (request > requestPerMinute) {
            System.out.println("Request limit exceeded!");
            return false;
        }
        return checkNext(email, password,phone);
    }

    public ThrottlingMiddleware(int requestPerMinute) {
        this.requestPerMinute = requestPerMinute;
        this.currentTime=System.currentTimeMillis();
    }

}
