package com.sogorae.jpaquerycounter.config;

import com.sogorae.jpaquerycounter.QueryCountInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final QueryCountInterceptor queryCountInterceptor;

    public WebConfig(final QueryCountInterceptor queryCountInterceptor) {
        this.queryCountInterceptor = queryCountInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(queryCountInterceptor)
                .addPathPatterns("/**");
    }
}
