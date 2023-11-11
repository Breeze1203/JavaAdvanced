package com.example.admin.util;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {

    /*
    经过代理以后，由于在客户端和服务之间增加了中间层，因此服务器无法直接拿到客户端的IP，
    服务器端应用也无法直接通过转发请求的地址返回给客户端。
    但是在转发请求的HTTP头信息中，增加了各种请求头信息。
    用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址
     */
    private static final String[] PROXY_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };

    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();

        for (String header : PROXY_HEADERS) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                ipAddress = ip;
                break;
            }
        }

        return ipAddress;
    }
}
