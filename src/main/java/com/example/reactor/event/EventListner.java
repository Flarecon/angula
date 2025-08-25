package com.example.reactor.event;

import com.example.angula.annotation.JobExecutor;
import com.example.reactor.event.events.PostCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@JobExecutor(format = "\n----- Event Triggered -----")
@Component
public class EventListner {

    @EventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        System.out.println("Post Creation Event Handled with ID: " + event.getPostId());
    }
}
