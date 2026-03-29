package com.example.angula.services.aws;

import com.example.angula.database.model.aws.SensorData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

// this is not recommended in production for mare than a instance
// this can produce racing condition issues

@Service
@Slf4j
public class TableInitializer {
    private final DynamoDbEnhancedClient enhancedClient;

    public TableInitializer(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @PostConstruct
    public void createTable() {
        DynamoDbTable<SensorData> table = enhancedClient.table("sensor_data", TableSchema.fromBean(SensorData.class));
        try {
            table.createTable(); // This creates the table based on your @DynamoDbBean annotations
            log.info("Table created successfully!");
        } catch (ResourceInUseException e) {
            log.error("Table exists or cannot create");
        }
    }
}