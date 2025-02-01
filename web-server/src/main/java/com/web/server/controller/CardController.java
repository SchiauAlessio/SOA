package com.web.server.controller;

import com.web.server.model.Card;
import com.web.server.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public List<Card> findAll() {
        logger.info("Request to get all cards");
        return this.cardService.findAll();
    }

    @PostMapping("/card/save")
    public ResponseEntity<Card> save(@RequestBody Card card) {
        logger.info("Request to save card: {}", card);
        return ResponseEntity.ok(this.cardService.save(card));
    }
}
