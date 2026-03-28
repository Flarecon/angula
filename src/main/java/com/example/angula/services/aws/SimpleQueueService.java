package com.example.angula.services.aws;

import com.example.angula.Constants;
import io.awspring.cloud.sns.core.SnsTemplate;
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
    private final SnsTemplate snsTemplate;

    @Value("${cloud.aws.sqs.url}")
    private String sqsUrl;

    @Value("${cloud.aws.sns.topic.email.arn}")
    private String snsTopicEmailArn;

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
        System.out.println("Processed SQS FIFO: " + message);
        snsTemplate.sendNotification(snsTopicEmailArn, message,
                "New SQS Message Notification");
        System.out.println("Forwarded SQS FIFO message");
    }
}
