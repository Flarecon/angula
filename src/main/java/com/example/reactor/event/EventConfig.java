package com.example.reactor.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

// TODO: fix this error handler
@Configuration
public class EventConfig {

    @Bean
    public ApplicationEventMulticaster eventMulticaster(){
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setErrorHandler(error -> {
            System.out.println("------------------------------------------------");
            System.out.println("Error in event listener : " + error.getMessage());
            System.out.println("------------------------------------------------");
        });

        return multicaster;
    }
}
