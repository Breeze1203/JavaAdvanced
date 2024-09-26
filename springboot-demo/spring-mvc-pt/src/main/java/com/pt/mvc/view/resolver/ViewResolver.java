package com.pt.mvc.view.resolver;

import com.pt.mvc.view.View;

public interface ViewResolver {
    View resolveViewName(String viewName) throws Exception;
}
