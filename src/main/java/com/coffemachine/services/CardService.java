package com.coffemachine.services;

import java.util.List;

import com.coffemachine.dto.CardBO;
import com.coffemachine.module.Card;

public interface CardService {
	
	public List<CardBO> getAllCards();
	
	public List<Card> getAllCardsByUserEmail(String email);
	
	public Card getCard(Long id);
	
	public void addCard(Card card);
	
	public void updateCard(Card card);

	public void deleteCard(Long id);
	
	public boolean isCardExist(Card card);

}
