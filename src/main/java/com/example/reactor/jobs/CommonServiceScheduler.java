package com.example.reactor.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.reactor.service.ServiceToService;

@Component
@ConditionalOnProperty(name = "reactor.scheduler.dynamic", havingValue = "true", matchIfMissing = true)
public class CommonServiceScheduler {

    @Autowired
    ServiceToService service;
    
    @Scheduled(fixedDelay = 30000, initialDelay = 5000)
    void checkBeanInitialization(){
        System.out.println("bean initializer with data running");
        try{
            service.getSheetDataAndRegisterBean();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
