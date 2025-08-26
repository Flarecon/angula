package com.example.reactor.event;

import com.example.angula.annotation.JobExecutor;
import com.example.reactor.event.events.PostCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@JobExecutor(format = "Event {method} of {class} Triggered at {timestamp}")
@Component
public class CommonEventListener {

    @EventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        System.out.println("Post Creation Event Handled with ID: " + event.getPostId());
    }
}
