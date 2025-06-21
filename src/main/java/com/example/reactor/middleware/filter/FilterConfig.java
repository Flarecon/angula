package com.example.reactor.middleware.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter1> loggingFilter1() {
        FilterRegistrationBean<LoggingFilter1> reg = new FilterRegistrationBean<>();
        reg.setFilter(new LoggingFilter1());
        reg.addUrlPatterns("/*");
        reg.setOrder(1);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter2> loggingFilter2() {
        FilterRegistrationBean<LoggingFilter2> reg = new FilterRegistrationBean<>();
        reg.setFilter(new LoggingFilter2());
        reg.addUrlPatterns("/*");
        reg.setOrder(2);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter3> loggingFilter3() {
        FilterRegistrationBean<LoggingFilter3> reg = new FilterRegistrationBean<>();
        reg.setFilter(new LoggingFilter3());
        reg.addUrlPatterns("/*");
        reg.setOrder(3);
        return reg;
    }
    
}
