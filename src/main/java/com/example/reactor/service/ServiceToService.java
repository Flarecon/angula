package com.example.reactor.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.angula.database.model.AngulaYt;
import com.example.angula.database.repository.YtRepo;
import com.example.reactor.jobs.LogScheduler;
import com.example.reactor.jobs.ServiceScheduler;

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
    public RestClient restClientYt;

    @Autowired
    @Qualifier("sheetClient")
    public RestClient restClientSheet;

    @Autowired
    public YtRepo ytRepo;

    @Autowired
    GenericApplicationContext context;

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;


    public void getSheetDataAndRegisterBean(){
        
        Class<?>[] classList = {ServiceScheduler.class, LogScheduler.class};

        List<SheetData> data = restClientSheet.get().uri("u5fpu5kogc31o").retrieve().body(new ParameterizedTypeReference<List<SheetData>>() {});
        if(data != null){

            for(SheetData sheet : data){
                if(sheet.enabled.equals("TRUE")){
                    if(!context.containsBean(sheet.bean)){
                        Integer classIndex = -1;
                        
                        try{
                        classIndex = Integer.parseInt(sheet.index);
                        }catch(NumberFormatException e){
                            classIndex = -1;
                        }
                        Class<?> className = sheet.index != null ? classList[classIndex] : null;
                        if (sheet.index != null && classIndex >= 0 && classIndex < classList.length){
                            context.registerBean(sheet.bean, className);
                            postProcessor.postProcessAfterInitialization(context.getBean(sheet.bean), sheet.bean);
                            System.out.println(sheet.bean + " Bean Created of class " + className.getName());
                        }
                    }
                }
                else if(sheet.enabled.equals("FALSE")){
                    if(context.containsBeanDefinition(sheet.bean)){
                        context.removeBeanDefinition(sheet.bean);
                        System.out.println(sheet.bean + " Bean Destroyed");
                    }
                }
            }
        }
    }

    @Transactional
    public void getPlaylistData() {
        int newVideo = 0;
        // System.out.println("key: " + key + " \nplaylistId: " + playlistId);
        YtResponse ytResponse = restClientYt.get()
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
class SheetData{
    String bean;
    String enabled;
    String index;
}
