package com.pt.mvc.view.resolver;

import com.pt.mvc.view.InternalResourceView;
import com.pt.mvc.view.View;

public class InternalResourceViewResolver extends UrlBasedViewResolver {
    @Override
    protected View buildView(String viewName) {
        String url = getPrefix() + viewName + getSuffix();
        return new InternalResourceView(url);
    }
}
