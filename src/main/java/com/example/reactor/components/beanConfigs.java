package com.example.reactor.components;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.reactor.middleware.interceptor.LoggingInterceptor;

@Configuration
public class beanConfigs implements WebMvcConfigurer{
    LocalDateTime now = LocalDateTime.now();

    @Bean
    String sugar(){
        return "Sugar is good for you! " + now;
    }

    @Bean
    Color color() {
        return Color.RED;
    }

    @Bean(name = "chocolate")
    @ConditionalOnProperty(prefix = "reactor", value = "sweet", havingValue = "yes")
    @Primary
    Sweet sweet1(){
        return new Sweet("chocolate", Color.BROWN);
    }

    @Bean(name = "cake")
    Sweet sweet2(){
        return new Sweet("cake", Color.YELLOW);
    }

    @Bean
    Sweet sweet3(){
        return new Sweet("icecream", Color.WHITE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/user/**");
        // WebMvcConfigurer.super.addInterceptors(registry);
    }

}
