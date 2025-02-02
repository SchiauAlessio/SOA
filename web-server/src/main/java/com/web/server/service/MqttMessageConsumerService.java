package com.web.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.web.server.model.Card;
import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttMessageConsumerService {

    @Autowired
    private CardService cardService;

    @Autowired
    private WebSocketNotificationService notificationService;

    @PostConstruct
    public void subscribeToTopic() {
        Mqtt5AsyncClient client = MqttClient.builder()
                .useMqttVersion5()
                .identifier("consumer-id")
                .serverHost("broker.hivemq.com") // "broker.hivemq.com"
                .serverPort(1883) // Default MQTT port
                .automaticReconnectWithDefaultConfig() // Enable automatic reconnect
                .buildAsync();

        client.connectWith()
                .cleanStart(true)
                .sessionExpiryInterval(500)
                .send()
                .whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Connection failed: " + throwable.getMessage());
                    } else {
                        System.out.println("Connected successfully");
                        // Subscribe to a topic
                        client.subscribeWith()
                                .topicFilter("SYSTEMCOMMUNICATION") // Change to your topic
                                .qos(MqttQos.AT_LEAST_ONCE)
                                .send()
                                .whenComplete((subAck, subThrowable) -> {
                                    if (subThrowable != null) {
                                        System.out.println("Subscribe failed: " + subThrowable.getMessage());
                                    } else {
                                        System.out.println("Subscribed successfully");
                                    }
                                });

                        // Set up callback for incoming messages
                        client.toAsync().publishes(MqttGlobalPublishFilter.ALL, publish -> {
                            try {
                                String message = new String(publish.getPayloadAsBytes());
                                ObjectMapper objectMapper = new ObjectMapper();
                                JsonNode jsonNode = objectMapper.readTree(message);
                                String extractedMessage = jsonNode.get("message").asText();

                                Card card = new Card();
                                card.setContent(extractedMessage);
                                card.setTitle("MQTT Message");
                                cardService.save(card);
                                System.out.println("Received message: " + message);
                                notificationService.sendCardSavedNotification(card);
                            } catch (Exception e) {
                                System.out.println("Failed to process message: " + e.getMessage());
                            }
                        });
                    }
                });
    }
}
