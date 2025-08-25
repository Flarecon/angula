package com.example.reactor.event.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PostCreatedEvent {
    private final String postId;
}