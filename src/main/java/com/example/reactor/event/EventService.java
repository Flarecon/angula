package com.example.reactor.event;

import com.example.reactor.event.events.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final ApplicationEventPublisher publisher;

    public String createPost(String id) {
        String successMessage = "post created by ID: " + id;
        System.out.println(successMessage);
        publisher.publishEvent(new PostCreatedEvent(id));
        return successMessage;
    }
}
