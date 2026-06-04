package com.example.angula.services.kafka;
import com.example.angula.Constants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

//    @KafkaListener(topics = Constants.KAFKA_TOPIC)
    public void consume(String message) {
        System.out.printf("<-- Received message: %s%n", message);
    }
}