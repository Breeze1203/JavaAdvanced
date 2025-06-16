package com.pt.mvc.handler.mapping;

import com.pt.mvc.annotation.RequestMapping;
import com.pt.mvc.http.RequestMethod;

public class RequestMappingInfo {
    private String path;
    private RequestMethod httpMethod;

    public RequestMappingInfo(String prefix, RequestMapping requestMapping) {
        this.path = prefix + requestMapping.path();
        this.httpMethod = requestMapping.method();
    }

    public String getPath() {
        return path;
    }

    public RequestMethod getHttpMethod() {
        return httpMethod;
    }


    @Override
    public String toString() {
        return "RequestMappingInfo{" +
                "path='" + path + '\'' +
                ", httpMethod=" + httpMethod +
                '}';
    }
}
