package com.libertymutual.blackjack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.libertymutual.blackjack.models.Card;
import com.libertymutual.blackjack.models.Dealer;
import com.libertymutual.blackjack.models.Deck;
import com.libertymutual.blackjack.models.FaceCard;
import com.libertymutual.blackjack.models.Hand;
import com.libertymutual.blackjack.models.Player;

@SpringBootApplication
public class BlackjackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackjackApplication.class, args);
//		
//		Deck deck = new Deck();
//		deck.shuffle();
//		
//		Hand hand = new Hand();
//		hand.addCard(deck.dealCard());
//		hand.addCard(deck.dealCard());
//		hand.addCard(deck.dealCard());
//		hand.printCard();
//		int[] values = hand.getValues();
//		System.out.println("Values: " + values[0] + ", " + values[1]);
//		
//		Player player1 = new Player("Josh", hand, 100);
//		int[] playerHandValues = player1.hand.getValues();
//		System.out.println("PlayerName: " + player1.getName()  + ", Wallet: " + player1.getWalletAmount() + ", HandValue: " + playerHandValues[0] + ", " + playerHandValues[1]);
//		player1.makeBet(5);
//		System.out.println("PlayerName: " + player1.getName()  + ", Wallet: " + player1.getWalletAmount() + ", HandValue: " + playerHandValues[0] + ", " + playerHandValues[1]);
//		
		
	}
}
