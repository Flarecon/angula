package com.example.angula.controller.aws;

import com.example.angula.services.aws.SimpleQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sqs")
public class SQSController {

    private final SimpleQueueService sqsService;

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String message) {
        sqsService.publish(message);
        return ResponseEntity.ok("message published");
    }

}
