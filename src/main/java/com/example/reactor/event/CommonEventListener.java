package com.example.reactor.event;

import com.example.angula.annotation.JobExecutor;
import com.example.reactor.error.CustomException;
import com.example.reactor.event.events.PostCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@JobExecutor(format = "Event {method} of {class} Triggered at {timestamp}")
@Component
public class CommonEventListener {

    /*
    working of async -> all async listeners will be assigned distinct threads to execute simultaneously
    working or order -> all order listeners will be work on same thread once after another order wise
    */

    @EventListener(condition = "#event.limit < 20")
//    @Order(1)
    @Async
    public void handleLog1PostCreatedEvent(PostCreatedEvent event) {
        System.out.println("Post Creation Event 1 Handled with POST ID: " + event.getPostId());
    }

    @EventListener(condition = "#event.limit > 1")
//    @Order(2)
    @Async
    public void handleLog2PostCreatedEvent(PostCreatedEvent event) {

        if(event.limit == 10)
            throw new RuntimeException("attention! event limit 10 will invoke handler 3");

        System.out.println("Post Creation Event 2 Handled with POST ID: " + event.getPostId());
    }

    @EventListener(condition = "#postEvent.limit == 10")
//    @Order(3)
    @Async
    public void handleLog3PostCreatedEvent(PostCreatedEvent postEvent) {
        System.out.println("Post Creation Event 3 Handled with POST ID: " + postEvent.getPostId());
    }
}
