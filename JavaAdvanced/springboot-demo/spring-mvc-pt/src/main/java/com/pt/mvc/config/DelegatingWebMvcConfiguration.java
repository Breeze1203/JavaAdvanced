package com.pt.mvc.config;

import com.pt.mvc.handler.argument.HandlerMethodArgumentResolver;
import com.pt.mvc.handler.interceptor.InterceptorRegistry;
import com.pt.mvc.handler.returnvalue.HandlerMethodReturnValueHandler;
import com.pt.mvc.view.View;
import com.pt.mvc.view.resolver.ViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
    private WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();

    @Autowired(required = false)
    public void setConfigurers(List<WebMvcConfigurer> configurers) {
        if (!CollectionUtils.isEmpty(configurers)) {
            this.configurers.addWebMvcConfigurers(configurers);
        }
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        configurers.addFormatters(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        configurers.addInterceptors(registry);
    }

    @Override
    protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        configurers.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        configurers.addArgumentResolvers(argumentResolvers);
    }

    @Override
    protected void addDefaultViews(List<View> views) {
        configurers.addDefaultViews(views);
    }

    @Override
    protected void addViewResolvers(List<ViewResolver> viewResolvers) {
        configurers.addViewResolvers(viewResolvers);
    }
}
