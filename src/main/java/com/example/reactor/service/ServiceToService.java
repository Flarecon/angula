package com.example.reactor.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.angula.database.model.AngulaYt;
import com.example.angula.database.repository.YtRepo;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceToService {

    @Value("${yt.key}")
    String key;

    @Value("${yt.playlist.key}")
    String playlistId;

    @Autowired
    @Qualifier("YtClient")
    public RestClient restClient;

    @Autowired
    public YtRepo ytRepo;

    @Transactional
    public void getPlaylistData() {
        int newVideo = 0;
        // System.out.println("key: " + key + " \nplaylistId: " + playlistId);
        YtResponse ytResponse = restClient.get()
                .uri("/playlistItems?part=snippet&maxResults=50&playlistId=" + playlistId + "&key=" + key).retrieve()
                .body(YtResponse.class);

        if(ytResponse == null || ytResponse.items.isEmpty()){ System.out.println("No results"); return;}
        for (YtResponse.Item item : ytResponse.items) {
            String title = item.snippet.title;
            String videoId = item.snippet.resourceId.videoId;
            try {
                ytRepo.save(AngulaYt.builder().videoId(videoId).videoTitle(title).build());
                newVideo++;
            }
            catch (DataIntegrityViolationException | ConstraintViolationException e) {
                log.info("Skipping duplicate entry");
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + " for videoId: " + videoId);
            }
        }
        System.out.println("Youtube Video Database Refreshed at " + LocalDateTime.now() + " with " + newVideo + " new videos");
    }
}

class YtResponse {
    public List<Item> items;

    public static class Item {
        public Snippet snippet;
    }

    public static class Snippet {
        public String title;
        public ResourceId resourceId;
    }

    public static class ResourceId {
        public String videoId;
    }
}
