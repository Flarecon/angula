package com.example.angula.controller.aws;

import com.example.angula.database.model.aws.SensorData;
import com.example.angula.services.aws.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sensor")
public class SensorDataController {

    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<SensorData> save(@RequestBody SensorData sensorData) {
        return ResponseEntity.ok(sensorService.save(sensorData));
    }

    @GetMapping
    public ResponseEntity<SensorData> get(String sensorId, Long timestamp) {
        return ResponseEntity.ok(sensorService.get(sensorId, timestamp));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(String sensorId, Long timestamp) {
        sensorService.delete(sensorId, timestamp);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/test-value") // connects to /config/angula/test.value of aws parameter store
    public ResponseEntity<String> getTestValue(@Value("${test.value}") String value) {
        return ResponseEntity.ok(value);
    }
}
