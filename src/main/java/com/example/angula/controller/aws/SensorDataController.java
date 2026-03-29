package com.example.angula.controller.aws;

import com.example.angula.database.model.aws.SensorData;
import com.example.angula.services.aws.SensorService;
import lombok.RequiredArgsConstructor;
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
}
