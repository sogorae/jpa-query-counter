package com.sogorae.jpaquerycounter;

import static java.lang.System.currentTimeMillis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                                final Object handler, final Exception ex) {
        long duration = jpaInspector.getDuration(currentTimeMillis());
        String result = getSqlQueriesResult(request, duration, jpaInspector.getQueriesResult());
        log.info("query result :\n{}", result);
        jpaInspector.clear();
        outputFile(result);
    }

    private String getSqlQueriesResult(final HttpServletRequest request, final long duration, final String result) {
        String url = "url: " + request.getMethod() + " " + request.getRequestURI() + "\n";
        String time = "time: " + duration + "\n";
        return url + time + result;
    }

    private void outputFile(String result) {
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        try (BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true))) {
            fw.write(result);
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
