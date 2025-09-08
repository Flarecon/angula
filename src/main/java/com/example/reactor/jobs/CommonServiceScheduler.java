package com.example.reactor.jobs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.angula.annotation.JobExecutor;
import com.example.reactor.error.ErrorTools;
import com.example.reactor.service.ServiceToService;

@JobExecutor(format = "{method} of {class} is Running at {timestamp}")
@ConditionalOnProperty(name = "reactor.scheduler.dynamic", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class CommonServiceScheduler {

    private final ServiceToService service;
    
    @Scheduled(cron = "0 * * * * *")
    public void checkBeanInitialization(){
        try{
            service.getSheetDataAndRegisterBean();
        }catch(Exception e){
            ErrorTools.logErrorData(e, "fetch sheetdb data");
        }
    }
}
