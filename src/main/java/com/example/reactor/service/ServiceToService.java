package com.example.reactor.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.angula.database.model.AngulaYt;
import com.example.angula.database.repository.YtRepo;
import com.example.reactor.jobs.LogScheduler;
import com.example.reactor.jobs.ServiceScheduler;

import jakarta.transaction.Transactional;

@Service
public class ServiceToService {

    @Value("${yt.key}")
    String key;

    @Value("${yt.playlist.key}")
    String playlistId;

    @Value("${sheet.url}")
    String sheetUrl;

    @Autowired
    @Qualifier("YtClient")
    public RestClient restClientYt;

    @Autowired
    @Qualifier("sheetClient")
    public RestClient restClientSheet;

    @Autowired
    public YtRepo ytRepo;

    @Autowired
    GenericApplicationContext context;

    public void getSheetDataAndRegisterBean() throws Exception {
        DefaultListableBeanFactory factory = context.getDefaultListableBeanFactory();
        int changes = 0;

        Class<?>[] classList = { ServiceScheduler.class, LogScheduler.class };

        List<SheetData> data = null;
        try {
            data = restClientSheet.get().uri(sheetUrl)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<SheetData>>() {
                    });
        } catch (Exception e) {
            System.out.println("cannot fetch excel data please check network");
        }
        if (data != null) {

            for (SheetData sheet : data) {
                if (sheet.enabled.equals("TRUE")) {
                    if (!factory.containsBean(sheet.bean)) {
                        Integer classIndex = -1;

                        try {
                            classIndex = Integer.parseInt(sheet.index);
                        } catch (NumberFormatException e) {
                            classIndex = -1;
                        }
                        Class<?> className = sheet.index != null ? classList[classIndex] : null;
                        if (sheet.index != null && classIndex >= 0 && classIndex < classList.length) {
                            Object bean = factory.createBean(className);
                            factory.registerSingleton(sheet.bean, bean);
                            System.out.println(sheet.bean + " Bean Created of class " + className.getName());
                            changes++;
                        }
                    }
                } else if (sheet.enabled.equals("FALSE")) {
                    if (factory.containsBean(sheet.bean)) {
                        Object bean = factory.getBean(sheet.bean);
                        factory.destroyBean(bean);
                        factory.destroySingleton(sheet.bean);
                        System.out.println(sheet.bean + " Bean Destroyed");
                        changes++;
                    }
                }
            }
            if (changes == 0)
                System.out.println("No changes Found in Excel");
        }
    }

    @Transactional
    public void getPlaylistData() {
        int created = 0;
        int updated = 0;
        int deleted = 0;

        YtResponse ytResponse = restClientYt.get()
                .uri("/playlistItems?part=snippet&maxResults=50&playlistId=" + playlistId + "&key=" + key).retrieve()
                .body(YtResponse.class);

        if (ytResponse == null || ytResponse.items.isEmpty()) {
            System.out.println("No results");
            return;
        }
        Map<String, String> videoMap = new HashMap<>();
        ytRepo.findAll().forEach(video -> videoMap.put(video.getVideoId(), video.getVideoTitle()));

        for (YtResponse.Item item : ytResponse.items) {
            String title = item.snippet.title;
            String videoId = item.snippet.resourceId.videoId;
            String existingData = videoMap.get(videoId);

            if (existingData == null) {
                // video not in database
                ytRepo.save(AngulaYt.builder().videoId(videoId).videoTitle(title).build());
                created++;
            } else {
                // video is in database
                videoMap.remove(videoId);
                if (existingData.equals(title))
                    continue;

                // title is updated
                ytRepo.updateTitleByVideoId(videoId, title);
                updated++;
            }
        }

        // deleting deleted data
        for (String videoId : videoMap.keySet()) {
            ytRepo.deleteByVideoId(videoId);
            deleted++;
        }

        System.out.println("Youtube Video Database Refreshed at " +
                LocalDateTime.now() + " with \n" +
                "created videos : " + created + "\n" +
                "updated videos : " + updated + "\n" +
                "deleted videos : " + deleted + "\n");
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

class SheetData {
    String bean;
    String enabled;
    String index;
}
