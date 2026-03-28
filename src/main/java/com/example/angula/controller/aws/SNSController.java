package com.example.angula.controller.aws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsByTopicRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.Subscription;
import software.amazon.awssdk.services.sns.model.UnsubscribeRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sns")
@Slf4j
public class SNSController {
    private final SnsClient snsClient;

    @Value("${cloud.aws.sns.topic.email.arn}")
    private String snsTopicEmailArn;

    @PostMapping("/add")
    public ResponseEntity<String> addSubscriber(@RequestParam String email) {
        SubscribeRequest request = SubscribeRequest.builder()
                .protocol("email")
                .endpoint(email)
                .topicArn(snsTopicEmailArn)
                .build();
        snsClient.subscribe(request);
        log.info("sent subscribe request to: {}", email);
        return ResponseEntity.ok("sent subscribe request");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeSubscriber(@RequestParam String email) {
        ListSubscriptionsByTopicRequest listRequest = ListSubscriptionsByTopicRequest
                .builder()
                .topicArn(snsTopicEmailArn)
                .build();
        String subscriptionArn = snsClient.listSubscriptionsByTopic(listRequest)
                .subscriptions()
                .stream()
                .filter(s -> s.endpoint().equalsIgnoreCase(email))
                .map(Subscription::subscriptionArn)
                .findFirst()
                .orElse(null);

        if (subscriptionArn == null) {
            return ResponseEntity.ok("subscriber not found");
        }

        UnsubscribeRequest unsubscribeRequest = UnsubscribeRequest.builder()
                .subscriptionArn(subscriptionArn)
                .build();
        snsClient.unsubscribe(unsubscribeRequest);
        log.info("unsubscribed email: {}", email);
        return ResponseEntity.ok("unsubscribed");

    }

}
