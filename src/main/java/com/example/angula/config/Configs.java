 package com.example.angula.config;

 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.servlet.config.annotation.CorsRegistry;
 import org.springframework.web.servlet.config.annotation.EnableWebMvc;
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

 @Configuration
 @EnableWebMvc
 public class Configs {
      @Bean
      WebMvcConfigurer corsConfigurer() {
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
      Gson gson(){
        return new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create();
      }
 }
