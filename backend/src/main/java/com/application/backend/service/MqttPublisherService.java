package com.application.backend.service;


import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {

    private final Mqtt5AsyncClient mqttClient;

    @Autowired
    public MqttPublisherService(Mqtt5AsyncClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void publishMessage(String message) {
        // Check if the client is already connected
        if (!mqttClient.getState().isConnected()) {
            // Connect if not already connected
            mqttClient.connectWith()
                    .cleanStart(true)
                    .sessionExpiryInterval(500)
                    .send()
                    .whenComplete((connAck, throwable) -> {
                        if (throwable != null) {
                            System.out.println("Connection failed: " + throwable.getMessage());
                        } else {
                            publish(message); // Call publish method after successful connection
                        }
                    });
        } else {
            // Directly publish if already connected
            publish(message);
        }
    }

    private void publish(String message) {
        mqttClient.publishWith()
                .topic("SYSTEMCOMMUNICATION")
                .payload(message.getBytes())
                .send()
                .whenComplete((publishAck, pubThrowable) -> {
                    if (pubThrowable != null) {
                        System.out.println("Publish failed: " + pubThrowable.getMessage());
                    } else {
                        System.out.println("Message published to topic SYSTEMCOMMUNICATION");
                    }
                });
    }
}
