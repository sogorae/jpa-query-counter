package com.sogorae.jpaquerycounter.config;

import com.sogorae.jpaquerycounter.JpaInspector;
import com.sogorae.jpaquerycounter.QueryCountInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public QueryCountInterceptor queryCountInterceptor() {
        return new QueryCountInterceptor(jpaInspector());
    }

    @Bean
    public JpaInspector jpaInspector() {
        return new JpaInspector();
    }
}
