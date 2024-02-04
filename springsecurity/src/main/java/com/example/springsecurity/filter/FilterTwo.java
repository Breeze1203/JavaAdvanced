package com.example.springsecurity.filter;

import javax.servlet.*;
import java.io.IOException;

public class FilterTwo implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("第二个拦截器");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
