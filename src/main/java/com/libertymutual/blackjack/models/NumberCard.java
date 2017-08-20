
package com.libertymutual.blackjack.models;

public class NumberCard implements Card {
	
	private String suit;
	private int value;
	
	public NumberCard(int value, String suit) {
		this.suit = suit;
		this.value = value;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public String getName() {
		return String.valueOf(value);
	}
	
	public int[] getValues() {
		return new int[] { value, value };
	}

}
