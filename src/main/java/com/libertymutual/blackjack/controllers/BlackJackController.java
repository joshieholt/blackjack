
package com.libertymutual.blackjack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libertymutual.blackjack.models.Deck;
import com.libertymutual.blackjack.models.Hand;
import com.libertymutual.blackjack.models.Player;

@Controller
@RequestMapping("/blackjack") 
public class BlackJackController {
	Deck deck;
	Player player1;
	Player dealer;
	int currentPot;
	double currentBet;
	boolean dontShowDealerCard;
	boolean doShowDealerCard;
	boolean doShowPlayerButtons;
	boolean showPlayAgainButton;
	double playerWinLossAmount;
	String messageToPlayer;
	
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
		model.addAttribute("dealerCard1", dealer.getCard(0).getName());
		model.addAttribute("dealerCard2", dealer.getCard(1).getName());
		model.addAttribute("dontShowDealerCard", dontShowDealerCard);
		model.addAttribute("doShowDealerCard" , doShowDealerCard);
		model.addAttribute("playerCards", player1.getAllCards());
		model.addAttribute("playerValues", player1.getValues());
		model.addAttribute("doShowPlayerButtons" , doShowPlayerButtons);
		model.addAttribute("remainingCards", deck.remainingCards());
		model.addAttribute("playerBestHand", player1.getBestHand());
		return "blackjack/blackjack";
		
	}
	
	@PostMapping ("/game/start") 
	public String startingTheGame(Model model) {
		deck = null;
		deck = new Deck();
		
		deck.shuffleDeck();
		
		Hand dealerHand = new Hand();
		player1 = null;
		dealer = new Player("Dealer", dealerHand, 0);
		currentPot = 0;
		showPlayAgainButton = true;
		model.addAttribute("walletAmount", 100);
		return "blackjack/new-game-form";
	}
	
	@PostMapping ("/game/bet")
	public String makeBet(double playerBet, String playerName){
		if (playerBet <= 0) {
			playerWinLossAmount = 0;
			return "redirect:/blackjack/game/please-bet-more";
		}
		currentBet = playerBet;
		Hand playerHand = new Hand();
		if (player1 == null) {
			player1 = new Player(playerName, playerHand, 100);
		} else {
			player1.clearHand();
			dealer.clearHand();
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
			player1.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		if (deck.hasCards()) {
			dealer.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		if (deck.hasCards()) {
			player1.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		if (deck.hasCards()) {
			dealer.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		dontShowDealerCard = true;
		doShowDealerCard = false;
		doShowPlayerButtons = true;
		return "redirect:/blackjack/game";		
	}

	@PostMapping ("/game/hit") 
	public String hitTheDeck() {
		if (deck.hasCards()) {
			player1.addCard(deck.dealCard());
		} else {
			return "redirect:/blackjack/game/no-more-cards";
		}
		
		if (player1.isBusted()) {
			return "redirect:/blackjack/game/determine-winner";
		} else {
			return "redirect:/blackjack/game";
		}
	}
	
	@PostMapping ("game/stand") 
	public String stand() { 
		while (dealer.getBestHand() < 17) {
			if (deck.hasCards()) {
				dealer.addCard(deck.dealCard());
			} else {
				return "redirect:/blackjack/game/no-more-cards";
			}
		}
		return "redirect:/blackjack/game/determine-winner";
	}
	
	@GetMapping("/game/determine-winner")
	public String determineWinner() {
		if (player1.isBusted()) {
			//lose bet
			playerWinLossAmount = 0;
			messageToPlayer = "You Busted! You lose your $" + currentBet + " bet.";
		} else if (player1.hasBlackJack() && !(dealer.hasBlackJack())) {
			//win 3-2
			playerWinLossAmount = currentBet * 1.5 + currentBet;
			messageToPlayer = "BlackJack!! You win $" + playerWinLossAmount + "!";
		} else if (player1.hasBlackJack() && dealer.hasBlackJack()) {
			//keep bet
			playerWinLossAmount = currentBet;
			messageToPlayer = "You both got BlackJack! You keep your $" + currentBet + " bet.";
		} else if (player1.getBestHand() <= 21 && dealer.isBusted()) {
			//win 2-1
			playerWinLossAmount = currentBet * 2;
			messageToPlayer = "The Dealer busted! You win $" + playerWinLossAmount + "!";
		} else if (player1.getBestHand() <= 21 && dealer.hasBlackJack()) {
			// idk - not in rules...but i'm rogue.  you lose your bet
			playerWinLossAmount = 0;
			messageToPlayer = "This wasn't in the rules - dealer got BlackJack and you did not bust. You lose your $" + currentBet + " bet.";
		} else if (player1.getBestHand() <= 21 && dealer.getBestHand() < player1.getBestHand()) {
			//win 2-1
			playerWinLossAmount = currentBet * 2;
			messageToPlayer = "You beat the Dealer! You win $" + playerWinLossAmount + "!";
		} else if (player1.getBestHand() < dealer.getBestHand() && dealer.getBestHand() <= 21) {
			//lose bet
			playerWinLossAmount = 0;
			messageToPlayer = "The Dealer beat you. You lose your $" + currentBet + " bet.";
		} else if (player1.getBestHand() == dealer.getBestHand()) {
			//keep bet
			playerWinLossAmount = currentBet;
			messageToPlayer = "You tied the Dealer.  You keep your $" + currentBet + " bet.";
		} else {
			//i don't know - take the bet though...cuz we can
			playerWinLossAmount = 0;
			messageToPlayer = "Something weird must have happened...but we're keeping your $" + currentBet + " bet for good measure.";
		}
		player1.adjustWalletAmount(playerWinLossAmount);
		return "redirect:/blackjack/game/end-hand";
	}
	
	@GetMapping("/game/end-hand")
	public String endHand(Model model) {
		currentPot = 0;
		model.addAttribute("messageToPlayer", messageToPlayer);
		model.addAttribute("walletAmount", player1.getWalletAmount());
		model.addAttribute("playerCards", player1.getAllCards());
		model.addAttribute("dealerCards", dealer.getAllCards());
		model.addAttribute("showPlayAgainButton", showPlayAgainButton);
		model.addAttribute("dealerBestHand", dealer.getBestHand());
		model.addAttribute("playerBestHand", player1.getBestHand());
		model.addAttribute("remainingCards", deck.remainingCards());
		return "blackjack/end-hand";
	}
	
	@GetMapping("/game/no-more-cards")
	public String noMoreCards() {
		showPlayAgainButton = false;
		messageToPlayer = "The deck is out of cards. Game Over!";
		playerWinLossAmount = currentBet;
		player1.adjustWalletAmount(playerWinLossAmount);
		return "redirect:/blackjack/game/end-hand";
	}
	
	@GetMapping("/game/no-more-money")
	public String noMoreMoney() {
		showPlayAgainButton = false;
		messageToPlayer = "You are out of money. Game Over!";
		player1.adjustWalletAmount(playerWinLossAmount);
		return "redirect:/blackjack/game/end-hand";
	}
	
	@GetMapping("/game/please-bet-less")
	public String pleaseBetLess(Model model) {
		showPlayAgainButton = true;
		messageToPlayer = "Please bet no more than $" + player1.getWalletAmount() + "!";
		player1.adjustWalletAmount(playerWinLossAmount);
		return "redirect:/blackjack/game/end-hand";
	}
	
	@GetMapping("/game/please-bet-more")
	public String pleaseBetMore(Model model) {
		showPlayAgainButton = true;
		messageToPlayer = "Please bet greater than $0!";
		player1.adjustWalletAmount(playerWinLossAmount);
		return "redirect:/blackjack/game/end-hand";
	}
	
	
}