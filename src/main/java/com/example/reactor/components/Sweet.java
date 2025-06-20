package com.example.reactor.components;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sweet{

    public static final String Color = null;
    String name;
    Color color;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        System.out.println("--------------------------\nSweet with name " + name + " created before me\n--------------------------");
    }

    @PreDestroy
    public void destroy() throws Exception {
        System.out.println("--------------------------\nSweet with color " + color + " destroyed after me\n--------------------------");
    }
    
}

enum Color{RED, GREEN, BLUE, YELLOW, ORANGE, BROWN, WHITE}