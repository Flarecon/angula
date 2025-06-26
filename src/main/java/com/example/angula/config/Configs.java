 package com.example.angula.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.benmanes.caffeine.cache.Caffeine;



 @Configuration
 @EnableWebMvc
 public class Configs {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @SuppressWarnings("null")
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    // @Bean
    public CacheManager cacheManager(){
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.registerCustomCache("client", 
            Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(30, TimeUnit.HOURS)
            .recordStats()
            .build());

        manager.registerCustomCache("task", 
            Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(30, TimeUnit.HOURS)
            .recordStats()
            .build());
        
        return manager;
    }
 }
