package com.pt.mvc;

import com.pt.mvc.http.RequestMethod;
import com.pt.mvc.handler.HandlerMethod;
import com.pt.mvc.handler.mapping.MappingRegistry;
import com.pt.mvc.handler.mapping.RequestMappingHandlerMapping;
import com.pt.mvc.handler.mapping.RequestMappingInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Main extends BaseJunit4Test {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Test
    public void test() {
        MappingRegistry mappingRegistry = requestMappingHandlerMapping.getMappingRegistry();

        String path = "/index/test";
        String path1 = "/index/test2";
        String path4 = "/test4";

        Assert.assertEquals(mappingRegistry.getPathHandlerMethod().size(), 2);

        HandlerMethod handlerMethod = mappingRegistry.getHandlerMethodByPath(path);
        System.out.println(handlerMethod);
        HandlerMethod handlerMethod2 = mappingRegistry.getHandlerMethodByPath(path1);
        System.out.println(handlerMethod2);
        HandlerMethod handlerMethod4 = mappingRegistry.getHandlerMethodByPath(path4);
        System.out.println(handlerMethod4);
        Assert.assertNull(handlerMethod4);
        Assert.assertNotNull(handlerMethod);
        Assert.assertNotNull(handlerMethod2);
        RequestMappingInfo mapping = mappingRegistry.getMappingByPath(path);
        RequestMappingInfo mapping2 = mappingRegistry.getMappingByPath(path1);
        System.out.println(mapping);
        System.out.println(mapping2);
        Assert.assertNotNull(mapping);
        Assert.assertNotNull(mapping2);
        Assert.assertEquals(mapping.getHttpMethod(), RequestMethod.GET);
        Assert.assertEquals(mapping2.getHttpMethod(), RequestMethod.POST);
    }

}
