package com.application.backend.config;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class MqttConfig {

    @Bean
    public Mqtt5AsyncClient mqttClient() {
        Mqtt5AsyncClient client = MqttClient.builder()
                .useMqttVersion5()
                .identifier(UUID.randomUUID().toString())
                .serverHost("broker.hivemq.com") // "broker.hivemq.com"
                .serverPort(1883)
                .addConnectedListener(new MqttClientConnectedListener() {
                    @Override
                    public void onConnected(MqttClientConnectedContext context) {
                        System.out.println("Connected to the HiveMQ MQTT Broker!");
                    }
                })
                .automaticReconnect()
                .initialDelay(500, java.util.concurrent.TimeUnit.MILLISECONDS)
                .maxDelay(1, java.util.concurrent.TimeUnit.MINUTES)
                .applyAutomaticReconnect()
                .buildAsync();
        return client;
    }
}
