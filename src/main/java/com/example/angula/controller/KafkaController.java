package com.example.angula.controller;

import com.example.angula.services.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/push")
    public ResponseEntity<String> pushMessage(@RequestBody Map<String, String> request) {
        String topic = request.get("topic");
        String message = request.get("message");
        kafkaProducerService.sendMessage(topic, message);
        return ResponseEntity.ok("message pushed");
    }
}
