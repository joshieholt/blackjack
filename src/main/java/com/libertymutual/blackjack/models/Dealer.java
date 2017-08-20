
package com.libertymutual.blackjack.models;

public class Dealer {
	
	private String name = "Dealer";
	public Hand hand;
	
	public Dealer(Hand hand) {
		this.hand = hand;
	}

	public String getName() {
		return name;
	}
}
