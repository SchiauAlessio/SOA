package com.application.backend.controller;

import com.application.backend.service.MqttPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class MqttController {

    @Autowired
    private MqttPublisherService mqttPublisherService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody String message) {
        System.out.println("Successfully received!");
        mqttPublisherService.publishMessage(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Successfully received!");

        return ResponseEntity.ok(response);
    }
}
