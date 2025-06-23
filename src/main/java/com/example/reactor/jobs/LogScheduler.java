package com.example.reactor.jobs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;

// @Component
@ConditionalOnProperty(name = "reactor.scheduler.log", havingValue = "true", matchIfMissing = true)
public class LogScheduler {

    public LogScheduler() {
        System.out.println("------------------------\nScheduler Initialised\n------------------------");
    }

    @Scheduled(fixedRate = 60000) // if func takes longer than 60 seconds, it will run again and cause overlap
    public void runEveryFixed60Seconds() {
        System.out.println(
                "------------------------\nScheduler running every  fixed 60 seconds\n------------------------");
    }

    @Scheduled(fixedDelay = 20000, // will run after delay of 5 seconds, from the last run completion means no
                                   // overlapping
            initialDelay = 10000 // its optional arg which will wait initially after running the application then
                                 // will run normally
    )
    public void runEveryDelay20Seconds() {
        System.out.println(
                "------------------------\nScheduler running every delay 20 seconds\n------------------------");
    }

    @Scheduled(cron = "0 */10 * * * *") // seconds(0-59) minutes(0-59) hours(0-23)
                                        // dayOfMonth(1-31) month(1-12) dayOfWeek(0-7)->(0 or 7 is Sunday)
    public void cronScheduler() {
        System.out.println("------------------------\nScheduler running every cron\n------------------------");
    }

    public void fetchYtData() {

    }
}
