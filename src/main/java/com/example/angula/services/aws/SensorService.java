package com.example.angula.services.aws;

import com.example.angula.database.model.aws.SensorData;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final DynamoDbTemplate dynamoDbTemplate;

    public SensorData save(SensorData data) {
        dynamoDbTemplate.save(data);
        return data;
    }
    public SensorData get(String sensorId, Long timestamp) {
        return dynamoDbTemplate.load(
                Key.builder().partitionValue(sensorId).sortValue(timestamp).build(),
                SensorData.class
        );
    }
    public void delete(String sensorId, Long timestamp) {
        dynamoDbTemplate.delete(
                Key.builder().partitionValue(sensorId).sortValue(timestamp).build(),
                SensorData.class
        );
    }
}
