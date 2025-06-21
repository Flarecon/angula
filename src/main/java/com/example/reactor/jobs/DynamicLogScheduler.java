package com.example.reactor.jobs;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class DynamicLogScheduler {

    private ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    
    public DynamicLogScheduler() {
        scheduler.initialize();
    }
    
}
