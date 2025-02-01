package com.web.server.service;

import com.web.server.model.Card;
import com.web.server.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> findAll() {
        List<Card> cards = this.cardRepository.findAll();
        Collections.reverse(cards);
        return cards;
    }

    @Override
    public Card save(Card card) {

        return this.cardRepository.save(card);
    }
}
