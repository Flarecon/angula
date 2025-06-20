package com.example.reactor.service;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.example.reactor.components.Sweet;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@ConditionalOnProperty(name = "reactor.service", havingValue = "true", matchIfMissing = true)
@Profile("dev")
public class SweetService {

    @PostConstruct
    void init(){
        System.out.println("------------------------\nSweet service is rising\n------------------------");
    }

    @PreDestroy
    void clean(){
        System.out.println("------------------------\nSweet service is falling\n------------------------");
    }

    public Sweet getSweet(Sweet sweet) {
        sweet.setName(sweet.getName() + " from service");
        return sweet;
    }
}
