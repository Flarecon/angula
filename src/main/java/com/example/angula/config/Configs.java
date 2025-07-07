 package com.example.angula.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
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
     private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
     private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

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

    @Bean
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

     @Bean
     public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
         return builder -> {
             builder.serializers(new LocalDateTimeSerializer(FORMATTER));
             builder.deserializers(new LocalDateTimeDeserializer(FORMATTER));
             builder.simpleDateFormat(DATETIME_FORMAT);
         };
     }

     @Bean 
     String name(){
        return "Angula";
     }

     @Bean
     CommandLineRunner run(String name){
        return arg -> {System.out.println("this is " + name + " at " + LocalDateTime.now());};
     }
 }
