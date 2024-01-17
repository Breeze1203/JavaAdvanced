package com.example.admin.config;

import com.example.admin.interceptor.CheckAuthorizationInterceptor;
import com.example.admin.interceptor.CheckPermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    CheckPermissionInterceptor checkPermissionInterceptor;

    @Autowired
    CheckAuthorizationInterceptor checkAuthorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> path=new ArrayList<>();
        path.add("/static/**");
        path.add("/login");
        path.add("/getVerification");
        path.add("/loginByPhone");
        path.add("/swagger-ui-custom.html");
        path.add("/swagger-ui/**");
        path.add("/swagger-resources/**");
        path.add("/v3/api-docs/**");
        registry.addInterceptor(checkAuthorizationInterceptor)
                .addPathPatterns("/**")   // 所有路径都被拦截
                .excludePathPatterns(path).order(1);
        // 将自定义的拦截器进行添加
        registry.addInterceptor(checkPermissionInterceptor).addPathPatterns("/**")   // 所有路径都被拦截
                .excludePathPatterns(path).order(2);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080") // 允许特定源访问
                .allowedMethods("*") // 允许的请求方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true) // 允许发送凭证信息（如 cookies）
                .maxAge(3600); // 设置响应的最大存活时间（缓存时间））
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
       }


}
