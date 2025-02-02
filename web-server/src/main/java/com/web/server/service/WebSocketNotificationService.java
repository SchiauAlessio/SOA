package com.web.server.service;

import com.web.server.model.Card;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendCardSavedNotification(Card card) {
        messagingTemplate.convertAndSend("/topic/cardSaved", card);
    }
}
