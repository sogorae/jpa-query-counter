package com.sogorae.jpaquerycounter;

import static com.sogorae.jpaquerycounter.OutputFile.*;
import static java.lang.System.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class QueryCountInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JpaInspector.class);

    private final JpaInspector jpaInspector;
    private final OutputFile outputFile;

    public QueryCountInterceptor(final JpaInspector jpaInspector) {
        this.jpaInspector = jpaInspector;
        this.outputFile = new OutputFile();
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
        throws Exception {
        jpaInspector.start();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler, final Exception ex) {
        if (ex != null) {
            log.info("Exception: {}", ex.getMessage());
            return;
        }
        long duration = jpaInspector.getDuration(currentTimeMillis());
        String result = generateSqlQueriesResult(request, duration, jpaInspector.getQueriesResult());
        log.info("query result :{}{}", LINE_SEPARATOR, result);
        jpaInspector.clear();
        outputFile.write(result);
    }

    private String generateSqlQueriesResult(final HttpServletRequest request, final long duration,
        final String queryResult) {
        String url = "url: " + request.getMethod() + " " + request.getRequestURI() + LINE_SEPARATOR;
        String time = "time: " + duration + LINE_SEPARATOR;
        return url + time + queryResult;
    }
}
