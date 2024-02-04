package com.example.springsecurity.config;

import com.example.springsecurity.filter.FilterFirst;
import com.example.springsecurity.filter.FilterTwo;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<FilterFirst> myFilter() {
        FilterRegistrationBean<FilterFirst> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FilterFirst());
        registrationBean.setOrder(1); // 设置过滤器的执行顺序
        registrationBean.addUrlPatterns("/*"); // 设置过滤器的URL模式
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<FilterTwo> myFilterTwo() {
        FilterRegistrationBean<FilterTwo> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FilterTwo());
        registrationBean.setOrder(2); // 设置过滤器的执行顺序
        registrationBean.addUrlPatterns("/*"); // 设置过滤器的URL模式
        return registrationBean;
    }
}
