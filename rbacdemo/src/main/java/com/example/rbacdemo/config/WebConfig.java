package com.example.rbacdemo.config;

import com.example.rbacdemo.interceptor.CheckPermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    CheckPermissionInterceptor checkPermissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 将自定义的拦截器进行添加
        registry.addInterceptor(checkPermissionInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.3.248:8080") // 允许特定源访问
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true) // 允许发送凭证信息（如 cookies）
                .maxAge(3600); // 设置响应的最大存活时间（缓存时间））
    }

}
