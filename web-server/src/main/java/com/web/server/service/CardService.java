package com.web.server.service;

import com.web.server.model.Card;

import java.util.List;

public interface CardService {

    List<Card> findAll();

    Card save(Card card);
}
