
package com.libertymutual.blackjack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libertymutual.blackjack.models.Dealer;
import com.libertymutual.blackjack.models.Deck;
import com.libertymutual.blackjack.models.Hand;
import com.libertymutual.blackjack.models.Player;

@Controller
@RequestMapping("/blackjack") 
public class BlackJackController {
	Deck deck = new Deck();
	Player player1;
	Dealer dealer;
	int currentPot;
	double currentBet;
	boolean dontShowDealerCard;
	boolean doShowDealerCard;
	boolean doShowPlayerButtons;
	boolean showPlayAgainButton;
	
	
	@GetMapping("")
	public String blackjackHome() {
		return "blackjack/start";
	}
	
	@PostMapping("")
	public String blackjackGoHome() {
		return "blackjack/start";
	}
	
	@GetMapping("/game") 
	public String blackjackhomemodel (Model model) {
		model.addAttribute("playerName" , player1.getName());
		model.addAttribute("walletAmount", player1.getWalletAmount());
		model.addAttribute("currentPot", currentPot);
		model.addAttribute("dealerCard1", dealer.hand.getCard(0).getName());
		model.addAttribute("dealerCard2", dealer.hand.getCard(1).getName());
		model.addAttribute("dontShowDealerCard", dontShowDealerCard);
		model.addAttribute("doShowDealerCard" , doShowDealerCard);
		model.addAttribute("playerCards", player1.hand.getAllCards());
		model.addAttribute("playerValues", player1.hand.getValues());
		model.addAttribute("doShowPlayerButtons" , doShowPlayerButtons);
		return "blackjack/blackjack";
		
	}
	
	@PostMapping ("/game/bet")
	public String makeBet(int playerBet, String playerName){
		Hand playerHand = new Hand();
		if (player1 == null) {
			player1 = new Player(playerName, playerHand, 100);
		} else {
			player1.hand.clearHand();
			dealer.hand.clearHand();
		}
		currentBet = playerBet;
		if (player1.getWalletAmount()==0) {
			return "redirect:/blackjack/game/no-more-money";
		} else if (playerBet > player1.getWalletAmount()) {
			return "redirect:/blackjack/game/please-bet-less";
		} else {
			player1.adjustWalletAmount(playerBet * -1);
		}
		currentPot += playerBet;
		
		if (deck.hasCards()) {
			dealer.hand.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		if (deck.hasCards()) {
			dealer.hand.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		if (deck.hasCards()) {
			player1.hand.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		if (deck.hasCards()) {
			player1.hand.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		dontShowDealerCard = true;
		doShowDealerCard = false;
		doShowPlayerButtons = true;
		return "redirect:/blackjack/game";
		
	}
	
	
	
	@PostMapping ("/game/start") 
	public String startingTheGame() {
		deck.shuffleDeck();
		
		Hand dealerHand = new Hand();
		player1 = null;
		dealer = new Dealer (dealerHand);
		currentPot = 0;
		showPlayAgainButton = true;
		return "blackjack/new-game-form";
	}

	@PostMapping ("/game/hit") 
	public String hitTheDeck() {
		int [] values = player1.hand.getValues();
		if (values[0] > 21 && values[1] > 21) {
			return "redirect:/blackjack/game/bust";
		} else {
			if (deck.hasCards()) {
				player1.hand.addCard(deck.dealCard());
			} else {
				return "redirect:/blackjack/game/no-more-cards";
			}
			int values2[] = player1.hand.getValues();
			if (values2[0] > 21 && values2[1] > 21) {
				return "redirect:/blackjack/game/bust";
			} else {
				return "redirect:/blackjack/game"; 
			}
		}
		
	}
	
	@PostMapping ("game/stand") 
	public String stand() { 
		return "redirect:/blackjack/game/deal-to-dealer";
	}
	
	@GetMapping ("game/deal-to-dealer")
	public String dealToDealer(Model model) {
		int [] values = dealer.hand.getValues();
		while (values[0] < 17 && values[1] < 17) {
			if (deck.hasCards()) {
				dealer.hand.addCard(deck.dealCard());
			} else {
				return "redirect:/blackjack/game/no-more-cards";
			}
			values = dealer.hand.getValues(); 
		}
		return "redirect:/blackjack/game/end-hand";
	}
	
	@GetMapping("/game/end-hand")
	public String endHand(Model model) {
		int[] dealerValues = dealer.hand.getValues();
		int[] playerValues = player1.hand.getValues();
		int dealerValueToUse;
		int playerValueToUse;
		double playerWinLossAmount;
		String messageToPlayer = "";
		if (dealerValues[0] == 21) {
			dealerValueToUse = dealerValues[0];
		} else if (dealerValues[1] == 21) {
			dealerValueToUse = dealerValues[1];
		} else if (dealerValues[0] > 21 && dealerValues[1] > 21) {
			dealerValueToUse = 99;
		} else if (dealerValues[0] < 22 && dealerValues[0] > dealerValues[1]) {
			dealerValueToUse = dealerValues[0];
		} else {
			dealerValueToUse = dealerValues[1];
		}
		
		if (playerValues[0] == 21) {
			playerValueToUse = playerValues[0];
		} else if (playerValues[1] == 21) {
			playerValueToUse = playerValues[1];
		} else if (playerValues[0] > 21 && playerValues[1] > 21) {
			playerValueToUse = 99;
		} else if (playerValues[0] < 22 && playerValues[0] > playerValues[1]) {
			playerValueToUse = playerValues[0];
		} else {
			playerValueToUse = playerValues[1];
		}
		
		if (dealerValueToUse == 99 && playerValueToUse == 21) {
			//win 3 to 2
			playerWinLossAmount = currentBet * 1.5 + currentBet;
			messageToPlayer = "You won $" + playerWinLossAmount + "!";
		} else if (dealerValueToUse == 99 && playerValueToUse < 21) {
			// win 2 to 1
			playerWinLossAmount = currentBet * 2;
			messageToPlayer = "You won $" + playerWinLossAmount + "!";
		} else if (dealerValueToUse < playerValueToUse) {
			//win 2 to 1
			playerWinLossAmount = currentBet * 2;
			messageToPlayer = "You won $" + playerWinLossAmount + "!";
		} else if (playerValueToUse == 21 && dealerValueToUse == 21) {
			// keep your money 
			playerWinLossAmount = currentBet;
			messageToPlayer = "You keep your $" + currentBet + " bet!";
		} else if (playerValueToUse == 21 && dealerValueToUse < 21 ) {
			//win 3 to 2 
			playerWinLossAmount = currentBet * 1.5 + currentBet;
			messageToPlayer = "You won $" + playerWinLossAmount + "!";
		} else if (dealerValueToUse >= playerValueToUse) {
			//lose your bet
			playerWinLossAmount = 0;
			messageToPlayer = "You lost your $" + currentBet + " bet.";
		} else {
			//keep your bet
			playerWinLossAmount = currentBet;
			messageToPlayer = "You keep your $" + currentBet + " bet!";
		}
		
		currentPot = 0;
		player1.adjustWalletAmount(playerWinLossAmount);
		model.addAttribute("messageToPlayer", messageToPlayer);
		model.addAttribute("walletAmount", player1.getWalletAmount());
		model.addAttribute("playerCards", player1.hand.getAllCards());
		model.addAttribute("dealerCards", dealer.hand.getAllCards());
		model.addAttribute("showPlayAgainButton", showPlayAgainButton);
		return "blackjack/end-hand";
	}
	
	@GetMapping("/game/bust")
	public String busted(Model model) {
		currentPot = 0;
		model.addAttribute("walletAmount", player1.getWalletAmount());
		model.addAttribute("playerCards", player1.hand.getAllCards());
		model.addAttribute("dealerCards", dealer.hand.getAllCards());
		model.addAttribute("messageToPlayer" , "you busted!");
		model.addAttribute("showPlayAgainButton", showPlayAgainButton);
		player1.hand.clearHand();
		dealer.hand.clearHand();
		return "blackjack/end-hand";
	}
	
	@GetMapping("/game/no-more-cards")
	public String noMoreCards(Model model) {
		currentPot = 0;
		showPlayAgainButton = false;
		model.addAttribute("walletAmount", player1.getWalletAmount());
		model.addAttribute("playerCards", player1.hand.getAllCards());
		model.addAttribute("dealerCards", dealer.hand.getAllCards());
		model.addAttribute("messageToPlayer" , "The deck is out of cards.  Game Over!");
		model.addAttribute("showPlayAgainButton", showPlayAgainButton);
		return "blackjack/end-hand";
	}
	
	@GetMapping("/game/no-more-money")
	public String noMoreMoney(Model model) {
		currentPot = 0;
		showPlayAgainButton = false;
		model.addAttribute("walletAmount", player1.getWalletAmount());
		model.addAttribute("playerCards", player1.hand.getAllCards());
		model.addAttribute("dealerCards", dealer.hand.getAllCards());
		model.addAttribute("messageToPlayer" , "You are out of money.  Game Over!");
		model.addAttribute("showPlayAgainButton", showPlayAgainButton);
		return "blackjack/end-hand";
	}
	
	@GetMapping("/game/please-bet-less")
	public String pleaseBetLess(Model model) {
		currentPot = 0;
		showPlayAgainButton = true;
		model.addAttribute("walletAmount", player1.getWalletAmount());
		model.addAttribute("playerCards", player1.hand.getAllCards());
		model.addAttribute("dealerCards", dealer.hand.getAllCards());
		model.addAttribute("messageToPlayer" , "Please bet no more than $" + player1.getWalletAmount() + "!");
		model.addAttribute("showPlayAgainButton", showPlayAgainButton);
		return "blackjack/end-hand";
	}
	
}