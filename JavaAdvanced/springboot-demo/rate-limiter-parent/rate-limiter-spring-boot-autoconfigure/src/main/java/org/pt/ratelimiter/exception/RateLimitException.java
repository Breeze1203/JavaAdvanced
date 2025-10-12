package org.pt.ratelimiter.exception;

/**
 * @ClassName RateLimitException
 * @Author pt
 * @Description
 * @Date 2025/10/9 21:01
 **/
public class RateLimitException extends RuntimeException{
    public RateLimitException(String message) {
        super(message);
    }
}
