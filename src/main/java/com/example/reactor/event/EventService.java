package com.example.reactor.event;

import com.example.reactor.event.events.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final ApplicationEventPublisher publisher;

    public String createPost(String id, Integer limit) {
        String successMessage = "post created by ID: " + id + " & limit : " + limit;
        System.out.println(successMessage);
        publisher.publishEvent(new PostCreatedEvent(id, limit));
        return successMessage;
    }
}
