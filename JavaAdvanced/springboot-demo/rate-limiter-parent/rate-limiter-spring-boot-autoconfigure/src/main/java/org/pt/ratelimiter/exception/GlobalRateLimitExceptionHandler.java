package org.pt.ratelimiter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @ClassName GlobalRateLimitExceptionHandler
 * @Author pt
 * @Description
 * @Date 2025/10/9 21:00
 **/
@RestControllerAdvice
public class GlobalRateLimitExceptionHandler {

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<Map<String, Object>> handleRateLimitException(RateLimitException e) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(Map.of("message", e.getMessage(), "status", HttpStatus.TOO_MANY_REQUESTS.value()));
    }
}
