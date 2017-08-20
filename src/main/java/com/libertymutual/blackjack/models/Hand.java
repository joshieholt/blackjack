
package com.libertymutual.blackjack.models;

import java.awt.List;
import java.util.ArrayList;

public class Hand {

	private ArrayList<Card> cards;
	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	public void addCard (Card card) {
		cards.add(card);
	}
	
	public Card getCard(int cardIndex) {
		return cards.get(cardIndex);
		
	}
	
	public ArrayList<String> getAllCards() {
		ArrayList<String> cardsList = new ArrayList<String>();
		for (Card c : cards) {
			cardsList.add(c.getName());
		}
		return cardsList;
	}
	
	public int[] getValues() {
		int[] sums = new int[] { 0, 0 };
		
		for (Card c : cards) {
			int[] values = c.getValues();
			sums[0] += values[0];
			sums[1] += values[1];
		}
		
		return sums;
	}
	
	public void clearHand() {
		while (!cards.isEmpty()) {
			cards.remove(0);
		}
	}
}