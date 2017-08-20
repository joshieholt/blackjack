
package com.libertymutual.blackjack.models;

public class Deck {
	
	private Card[] cards;
	private int currentCardIndex;
	
	public Deck() {
		String[] suits = new String[] { "clubs", "spades", "hearts", "diamonds"};
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
}





//import java.util.Collections;
//import java.util.Stack;
//
//public class Deck {
//
//	private Card[] cardArray;
//	private Stack<Card> cardStack;
//	
//	public Deck() {
//		String[] suit = new String[] { "Club", "Diamond", "Heart", "Spade" };
//		cardStack = new Stack<Card>();
//		cardArray = new Card[52];
//		int i = 0;
//		
//		for (String s : suit) {
//			cardArray[i] = new AceCard(s);
//			i += 1;
//			
//			cardArray[i] = new FaceCard(s, "King");
//			i += 1;
//			cardArray[i] = new FaceCard(s, "Queen");
//			i += 1;
//			cardArray[i] = new FaceCard(s, "Jack");
//			i += 1;
//			
//			for (int j = 2; j < 11; j += 1) {
//				cardArray[i] = new NumberCard(s, j);
//				i += 1;
//			}
//		}
//		pushCardsOntoCardStackFromCardArray();
//	}
//	
//	
//	public void shuffle() {
//		for (int i = 0; i < 7; i += 1) {
//			for (int j = 0; j < 52; j += 1) {
//				Card tempCard;
//				int k = j + (int)(Math.random() * (52-j));
//				tempCard = cardArray[k];
//				cardArray[k] = cardArray[j];
//				cardArray[j] = tempCard;
//			}
//		}
//		
//		popCardsOffOfCardStack();
//		pushCardsOntoCardStackFromCardArray();
//		
//	}
//	
//	public void printCards() {
//		for (int i = 0; i < cardStack.size(); i += 1) {
//			Card tempCard = cardStack.elementAt(i);
//			System.out.println((i+1) + "; " + tempCard.getName() + " " + tempCard.getSuit());
//		}
//	}
//	
//	private void pushCardsOntoCardStackFromCardArray() {
//		for (int i = 0; i < 52; i += 1) {
//			cardStack.push(cardArray[i]);
//		}
//	}
//	
//	private void popCardsOffOfCardStack() {
//		while (cardStack.size() > 0) {
//			cardStack.pop();
//		}
//	}
//	
//	public Card dealCard() {
//		return cardStack.pop();		
//	}
//}
