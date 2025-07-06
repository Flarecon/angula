package com.example.reactor.jobs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.angula.annotation.JobExecutor;
import com.example.reactor.error.ErrorTools;
import com.example.reactor.service.ServiceToService;

@JobExecutor(format = "yt fetcher running at {timestamp}")
@ConditionalOnProperty(name = "reactor.scheduler.yt", havingValue = "true", matchIfMissing = true)
public class ServiceScheduler {

    ServiceToService serviceCaller;

    public ServiceScheduler(ServiceToService serviceCaller) {
        this.serviceCaller = serviceCaller;
    }

    // @Scheduled(cron = "0 */50 * * * *")
    @Scheduled(fixedDelay = 1000*60*1) // 1 minute
    public void fetchYtData() {
        try {
            serviceCaller.getPlaylistData();
        } catch (Exception e) {
            ErrorTools.logErrorData(e, "fetch yt data");
        }
    }
}
