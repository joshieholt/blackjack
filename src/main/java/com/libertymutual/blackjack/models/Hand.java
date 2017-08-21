
package com.libertymutual.blackjack.models;

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
	
	public boolean isBusted() {
		int[] values = getValues();
		if (values[0] > 21 && values[1] > 21) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasBlackJack() {
		if (cards.size() != 2) {
			return false;
		} else if (getBestHand() == 21) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getBestHand() {
		int[] values = getValues();
		if (values[0] == 21 || values[1] == 21) {
			return 21;
		} else if (values[0] < 22 && values[0] > values[1]) {
			return values[0];
		} else if (values[1] > 21) {
			return values[0];
		} else {
			return values[1];
		}
	}
}