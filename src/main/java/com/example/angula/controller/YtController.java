package com.example.angula.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.angula.database.model.AngulaYt;
import com.example.angula.database.repository.YtRepo;
import com.example.reactor.views.YtVideoData;
import com.example.reactor.views.YtVideoRecord;

@RestController
@RequiredArgsConstructor
@RequestMapping("/video")
public class YtController {

    private final YtRepo ytRepo;

    @GetMapping
    public List<AngulaYt> getAllVideos() {
        return ytRepo.findAll();
    }

    @GetMapping("/{id}")
    public YtVideoRecord getVideoById(@PathVariable("id") Long id) {
        AngulaYt video = ytRepo.findById(id).orElseThrow();
        return new YtVideoRecord(
                video.getId(),
                video.getVideoTitle(),
                "https://i.ytimg.com/vi/" + video.getVideoId() + "/sddefault.jpg",
                "https://youtu.be/" + video.getVideoId(),
                "https://www.youtube.com/embed/" + video.getVideoId()
            );
    }

    @GetMapping("/get/{id}")
    public YtVideoData getVideoByVideoId(@PathVariable("id") String id) {
        AngulaYt video = ytRepo.findByVideoId(id).orElseThrow();
        return YtVideoData.builder()
        .id(video.getId())
        .embedUrl("https://www.youtube.com/embed/" + video.getVideoId())
        .videoUrl("https://youtu.be/" + video.getVideoId())
        .thumbnailUrl("https://i.ytimg.com/vi/" + video.getVideoId() + "/sddefault.jpg")
        .title(video.getVideoTitle())
        .build();
    }

    @DeleteMapping("/{id}")
    public void deleteVideo(@PathVariable("id") Long id) {
        ytRepo.deleteById(id);
    }
}