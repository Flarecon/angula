package com.example.reactor.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.reactor.service.ServiceToService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@ConditionalOnProperty(name = "reactor.scheduler.yt", havingValue = "true", matchIfMissing = true)
public class Scheduler {
    
    @Autowired
    ServiceToService serviceCaller;

    @Scheduled(cron = "0 0 */15 * * *")
    public void fetchYtData() {
        log.info("Scheduler running for video refresh");
        serviceCaller.getPlaylistData();
    }
}
