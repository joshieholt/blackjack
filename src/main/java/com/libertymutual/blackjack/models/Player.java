
package com.libertymutual.blackjack.models;

import java.util.ArrayList;

public class Player {
	
	private String name;
	private Hand hand;
	private int wallet;
	
	public Player(String name, Hand hand, int wallet) {
		this.name = name;
		this.hand = hand;
		this.wallet = wallet;
	}
	
	public String getName() {
		return name;
	}
	
	public void adjustWalletAmount(double amount) {
		wallet += amount;
	}
	
	public int getWalletAmount() {
		return wallet;
	}
	
	public Card getCard(int cardIndex) {
		return hand.getCard(cardIndex);
	}
	
	public int[] getValues() {
		return hand.getValues();
	}
	
	public ArrayList<String> getAllCards() {
		return hand.getAllCards();
	}
	
	public void clearHand() {
		hand.clearHand();
	}
	
	public void addCard(Card card) {
		hand.addCard(card);
	}
	
	public boolean isBusted() {
		return hand.isBusted();
	}
	
	public boolean hasBlackJack() {
		return hand.hasBlackJack();
	}
	
	public int getBestHand() {
		return hand.getBestHand();
	}

}
