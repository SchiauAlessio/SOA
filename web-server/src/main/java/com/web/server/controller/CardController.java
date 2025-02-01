package com.web.server.controller;

import com.web.server.model.Card;
import com.web.server.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/soa")
public class CardController {

    private final CardService cardService;

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/cards")
    @PreAuthorize("hasRole('USER')")
    public List<Card> findAll() {
        logger.info("Request to get all cards");
        return this.cardService.findAll();
    }
}
