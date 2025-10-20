package org.pengtao.backendabstraction.common;

public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    // 构造函数, 静态工厂方法 (success, error)
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = 200;
        response.message = "Success";
        response.data = data;
        return response;
    }

    public static <T> ApiResponse<T> error(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = 500;
        response.message = message;
        response.data = data;
        return response;
    }
}
