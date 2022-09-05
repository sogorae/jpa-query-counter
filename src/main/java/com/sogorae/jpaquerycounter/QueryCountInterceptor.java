package com.sogorae.jpaquerycounter;

import static java.lang.System.currentTimeMillis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class QueryCountInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JpaInspector.class);

    private final JpaInspector jpaInspector;

    public QueryCountInterceptor(final JpaInspector jpaInspector) {
        this.jpaInspector = jpaInspector;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        jpaInspector.start();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex)
            throws Exception {
        Counter counter = jpaInspector.getCount();
        long duration = currentTimeMillis() - counter.getTime();
        long count = counter.getCount().get();
        log.info("time : {}, count : {} , url : {}", duration, count, request.getRequestURI());
        jpaInspector.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
