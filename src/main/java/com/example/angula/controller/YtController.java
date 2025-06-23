package com.example.angula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import com.example.angula.database.model.AngulaYt;
import com.example.angula.database.repository.YtRepo;
import com.example.reactor.views.YtVideoData;

@RestController
@RequestMapping("/video")
public class YtController {

    @Autowired
    YtRepo ytRepo;

    @GetMapping
    public List<AngulaYt> getAllVideos() {
        return ytRepo.findAll();
    }

    @GetMapping("/{id}")
    public YtVideoData getVideoById(@PathVariable("id") Long id) {
        AngulaYt video = ytRepo.findById(id).get();
        return YtVideoData.builder()
        .id(video.getId())
        .embedUrl("https://www.youtube.com/embed/" + video.getVideoId())
        .videoUrl("https://youtu.be/" + video.getVideoId())
        .thumbnailUrl("https://i.ytimg.com/vi/" + video.getVideoId() + "/sddefault.jpg")
        .title(video.getVideoTitle())
        .build();
    }

    @GetMapping("/get/{id}")
    public YtVideoData getVideoByVideoId(@PathVariable("id") String id) {
        AngulaYt video = ytRepo.findByVideoId(id).get();
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