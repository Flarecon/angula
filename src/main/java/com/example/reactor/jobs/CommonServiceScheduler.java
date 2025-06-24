package com.example.reactor.jobs;

import java.time.LocalDateTime;

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
    
    @Scheduled(cron = "0 * * * * *")
    void checkBeanInitialization(){
        System.out.println("bean initializer with data running at " + LocalDateTime.now());
        try{
            service.getSheetDataAndRegisterBean();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
