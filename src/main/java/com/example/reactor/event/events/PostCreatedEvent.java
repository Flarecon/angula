package com.example.reactor.event.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class PostCreatedEvent {
    private String postId;
    public Integer limit;
}