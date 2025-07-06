package com.example.reactor.views;

public record YtVideoRecord(
        Long id, 
        String title, 
        String thumbnailUrl, 
        String videoUrl, 
        String embedUrl
    ) {}
