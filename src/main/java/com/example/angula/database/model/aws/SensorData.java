package com.example.angula.database.model.aws;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Setter
public class SensorData {
    private String sensorId; // Partition Key
    private Long timestamp; // Sort Key

    @Getter
    private String payload;

    @DynamoDbPartitionKey
    public String getSensorId() { return sensorId; }

    @DynamoDbSortKey
    public Long getTimestamp() { return timestamp; }

}
