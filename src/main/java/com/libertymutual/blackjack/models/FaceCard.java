
package com.libertymutual.blackjack.models;

public class FaceCard implements Card {
	
	private String suit;
	private String name;
	
	public FaceCard(String name, String suit) {
		this.suit = suit;
		this.name = name;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public String getName() {
		return name;
	}
	
	public int[] getValues() {
		return new int[] { 10, 10 };
	}
}
