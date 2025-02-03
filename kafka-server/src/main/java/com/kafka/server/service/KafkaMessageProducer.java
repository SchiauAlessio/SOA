package com.kafka.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KafkaMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageProducer.class);

    public KafkaMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 60000) // Send message every 60 seconds
    public void sendMessage() {

        int winnerNumber = random.nextInt(4) + 1;
        String message = "The current winner is " + winnerNumber + "!";

        kafkaTemplate.send("message-topic", message);
        System.out.println("Sent: " + message);
    }
}
