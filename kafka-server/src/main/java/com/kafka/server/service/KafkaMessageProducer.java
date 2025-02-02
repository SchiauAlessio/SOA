package com.kafka.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final long startTime = System.currentTimeMillis();
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageProducer.class);

    public KafkaMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 20000) // Send message every 20 seconds
    public void sendMessage() {
        long secondsElapsed = (System.currentTimeMillis() - startTime) / 1000;
        String message = "It's been " + secondsElapsed + " seconds since Kafka instance is running!";

        kafkaTemplate.send("message-topic", message);
        System.out.println("Sent: " + message);
    }
}
