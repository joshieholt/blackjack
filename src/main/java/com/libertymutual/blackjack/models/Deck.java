
package com.libertymutual.blackjack.models;

public class Deck {
	
	private Card[] cards;
	private int currentCardIndex;
	
	public Deck() {
		String[] suits = new String[] { "Clubs", "Spades", "Hearts", "Diamonds"};
	int i = 0;
	cards = new Card[52];
	currentCardIndex = 0;
	
	for (String sts : suits) {
		cards[i] = new AceCard(sts);
		i += 1;
		
		cards[i] = new FaceCard("King", sts);
		i += 1;
		
		cards[i] = new FaceCard("Jack", sts);
		i += 1;
		
		cards[i] = new FaceCard("Queen", sts);
		i += 1;
		
		for (int r = 2; r < 11; r += 1) {
			cards[i] = new NumberCard(r, sts);
			i += 1;
		
			}
		}
	
	}
	
	public void printThis( ) {
		for (Card card : cards) {
			System.out.println(card.getName() + " " + card.getSuit());
		}
			}
	
	public void shuffleDeck() {
		for (int plzShuffle = 0; plzShuffle < 7; plzShuffle += 1) {
			Card[] tempCardHolder1 = new Card[26];
			Card[] tempCardHolder2 = new Card[26];
			
			for (int i = 0; i < tempCardHolder1.length; i += 1) {
				tempCardHolder1[i] = cards[i];
				tempCardHolder2[i] = cards [26 + i];
			}
	
	int holdsCard1 = 0;
	int holdsCard2 = 0;
	int holdsAll = 0;
	
	while (holdsCard1 < 26 || holdsCard2 < 26) {
		Card cardThatMoves;
		
		if(Math.random() < 0.5) {
			if (holdsCard1 < 26) {
				cardThatMoves = tempCardHolder1[holdsCard1];
				holdsCard1 += 1;
			} else {
				cardThatMoves = tempCardHolder2[holdsCard2];
				holdsCard2 += 1; }
		} else { 
			 	
				if (holdsCard2 < 26) {
					cardThatMoves = tempCardHolder2[holdsCard2];
					holdsCard2 += 1;
				} else {
					cardThatMoves = tempCardHolder1[holdsCard1];
					holdsCard1 += 1;
				}
				
			}
		
		cards[holdsAll] = cardThatMoves;
		holdsAll += 1;
			
		}
	}
			
		}
	
	public Card dealCard() {
		currentCardIndex += 1;
		return cards[currentCardIndex -1];
	}
		
	public boolean hasCards() {
		if (currentCardIndex < 52) {
			return true;
		} else {
			return false;
		}
	}
	
	public int remainingCards() {
		return cards.length - currentCardIndex;
	}
}

