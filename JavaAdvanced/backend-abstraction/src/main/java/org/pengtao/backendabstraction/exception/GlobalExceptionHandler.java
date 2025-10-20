package org.pengtao.backendabstraction.exception;

import org.pengtao.backendabstraction.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理我们自定义的 "资源未找到" 异常
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // 1. 创建 ApiResponse 错误对象
        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage()
        );

        // 2. ApiResponse 包装在 ResponseEntity 中，并设置 HTTP 状态码为 404
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 处理 Spring Validation (JSR 303) 校验失败的异常
     * (例如 @Valid @RequestBody 失败)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 从异常中提取第一个校验错误信息
        String firstErrorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                firstErrorMessage
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 兜底处理：处理所有其他未被捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {
        // 在服务器日志中打印详细堆栈信息，方便排查
        ex.printStackTrace();
        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "An unexpected error occurred: " + ex.getMessage() // 不要暴露过多内部细节给客户端
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}