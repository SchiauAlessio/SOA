package com.application.backend.controller;

import com.application.backend.service.MqttPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MqttController {

    @Autowired
    private MqttPublisherService mqttPublisherService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        System.out.println("Successfully received!");
        mqttPublisherService.publishMessage(message);
        String receivedMessage = "Successfully received!";
        return ResponseEntity.ok(receivedMessage);

    }
}
