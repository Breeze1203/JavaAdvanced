package com.pt.mvc.view.resolver;

import com.pt.mvc.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractCachingViewResolver implements ViewResolver {
    private final Object lock = new Object();
    private static final View UNRESOLVED_VIEW = (model, request, response) -> {
    };
    private Map<String, View> cachedViews = new HashMap<>();

    @Override
    public View resolveViewName(String viewName) throws Exception {
        View view = cachedViews.get(viewName);
        if (Objects.nonNull(view)) {
            return (view != UNRESOLVED_VIEW ? view : null);
        }

        synchronized (lock) {
            view = cachedViews.get(viewName);
            if (Objects.nonNull(view)) {
                return (view != UNRESOLVED_VIEW ? view : null);
            }

            view = createView(viewName);
            if (Objects.isNull(view)) {
                view = UNRESOLVED_VIEW;
            }
            cachedViews.put(viewName, view);
        }
        return (view != UNRESOLVED_VIEW ? view : null);
    }

    protected abstract View createView(String viewName);

}
