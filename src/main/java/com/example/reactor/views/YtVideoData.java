package com.example.reactor.views;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YtVideoData {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String videoUrl;
    private String embedUrl;
}
