package com.example.reactor.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.angula.annotation.JobExecutor;
import com.example.reactor.service.ServiceToService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@JobExecutor(format = "yt fetcher running at {timestamp}")
@ConditionalOnProperty(name = "reactor.scheduler.yt", havingValue = "true", matchIfMissing = true)
public class ServiceScheduler {
    
    @Autowired
    ServiceToService serviceCaller;

    @Scheduled(cron = "0 0 */15 * * *")
    public void fetchYtData() {
        log.info("Scheduler running for video refresh");
        serviceCaller.getPlaylistData();
    }
}
