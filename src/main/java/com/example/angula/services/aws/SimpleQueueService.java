package com.example.angula.services.aws;

import com.example.angula.Constants;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimpleQueueService {

    private final SqsTemplate sqsTemplate;

    @Value("${cloud.aws.sqs.url}")
    private String sqsUrl;

    public void publish(String message) {
        sqsTemplate.send(to -> to
                .queue(sqsUrl)
                .payload(message)
                .header("message-group-id", Constants.SQS_MESSAGE_GROUP_ID)
                .header("message-deduplication-id", UUID.randomUUID().toString())
        );
    }

    @SqsListener("${cloud.aws.sqs.name}")
    public void listen(String message) {
        System.out.println("Processing FIFO message: " + message);
    }
}
