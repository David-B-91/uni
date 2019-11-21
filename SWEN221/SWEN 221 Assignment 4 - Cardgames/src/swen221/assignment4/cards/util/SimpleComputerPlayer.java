package swen221.assignment4.cards.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import swen221.assignment4.cards.core.Card;
import swen221.assignment4.cards.core.Hand;
import swen221.assignment4.cards.core.Player;
import swen221.assignment4.cards.core.Trick;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer implements Serializable{

	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	@Override
	public Card getNextCard(Trick trick) {		
		
		/* variables to account for when deciding play */
		Hand compHand = player.getHand(); 
		List<Card> cardsPlayed = trick.getCardsPlayed();
		Card first = null;
				
		if (!cardsPlayed.isEmpty()){ //case that cards have been played.
			
			first = cardsPlayed.get(0); //for checking suit.
			Set <Card> cardsInSuit = compHand.matches(first.suit());
			
			if (!cardsInSuit.isEmpty()) { //can follow suit
				
				Object[] cardsInSuitArray=  cardsInSuit.toArray();
				Card highestInSuit = (Card) cardsInSuitArray[0];
			
				Card lowestInSuit = (Card) cardsInSuitArray[0];
				Card highestCaseWin = (Card) cardsInSuitArray[0];
				
				/*  Assign first to be highest value card played so far.  */
				for (Card c : cardsPlayed){
					if (first.compareTo(c) == -1){
						first = c; 
					}
				} 
				
				/*  Assign the lowest and highest in suit cards into corresponding variables.  */
				for (int i = 0 ; i < cardsInSuitArray.length ; i++){
				//	System.out.println("In LOOP");
					if (((Card) cardsInSuitArray[i]).compareTo(highestInSuit) == 1){
						highestInSuit = (Card) cardsInSuitArray[i];
				//		System.out.println("highLOOP: "+highestInSuit);
					}
					
					if (((Card) cardsInSuitArray[i]).compareTo(lowestInSuit) == -1){
						lowestInSuit = (Card) cardsInSuitArray[i];
				//		System.out.println("lowLOOP: "+lowestInSuit);
					}
					
					/*  if computer is last to play, find first card in hand that has higher rank than highest played card, and play that. */
					if (cardsPlayed.size() == 3){
						if (((Card) cardsInSuitArray[i]).compareTo(first) == 1){
							highestCaseWin = (Card) cardsInSuitArray[i];
					//		System.out.println("highCW: "+highestCaseWin);
							return highestCaseWin;
						}
					}
				} 
				//System.out.println("highEND: "+highestInSuit);
				//System.out.println("lowEND: "+lowestInSuit);
				
				/*  play highest card if would beat all cards played so far, play lowest if would lose the trick  */
				if (highestInSuit.compareTo(first) == 1){
					return highestInSuit;
				} else if (highestInSuit.compareTo(first) <= 0){
					return lowestInSuit;
				}
				
			} else { //cannot follow suit.
				
			//	System.out.println("CANT FOLLOW SUIT");
				
				Set <Card> cardsInTrumps = compHand.matches(trick.getTrumps());
				Card playedTrumps = null;
				
				if (!cardsInTrumps.isEmpty()){ // can play trumps
				//	System.out.println("can play trumps");
					Object[] trumpsArray = cardsInTrumps.toArray();
					Card highestTrumps = (Card)trumpsArray[0];
					Card highestToWin =  (Card)trumpsArray[0];
					
				/*  evaluate highest rank played trumps card (if there are any)  */
					for (Card c : cardsPlayed){
						if(c.suit().ordinal() == trick.getTrumps().ordinal()){
							if(playedTrumps == null){
								playedTrumps = c;
							} else {
								if (playedTrumps.rank().ordinal() < c.rank().ordinal()){
									playedTrumps = c;
								}
							}
						}
					}
					
					if (cardsPlayed.size() == 3){
						for (int i = 0; i < trumpsArray.length ; i++){
							if (playedTrumps != null && ((Card) trumpsArray[i]).rank().ordinal() > playedTrumps.rank().ordinal()){
								highestToWin = (Card) trumpsArray[i];
								return highestToWin; 
								
							}
						}
					}
					
					
						for (int i = 0; i < trumpsArray.length ; i++){
							
							if (((Card) trumpsArray[i]).rank().ordinal() == highestTrumps.rank().ordinal()){
								highestTrumps = (Card)trumpsArray[i];
							//	System.out.println("highTrumps: "+highestTrumps);
							}
						}
					}
					
					/*  case discard lowest  */
					Iterator<Card> itr = compHand.iterator();
					if (itr.hasNext()){
						Card lowestInHand = itr.next();
						while (itr.hasNext()){
							Card compareCard = itr.next();
							if(lowestInHand.rank().ordinal() > (compareCard.rank().ordinal())){
								lowestInHand = compareCard;
							}
						}
						return lowestInHand;
					}
				
			}
		} else { //no cards have been played.
			Iterator<Card> itr = compHand.iterator();
			if (itr.hasNext()){
				Card bestInHand = itr.next();
				
								
				while (itr.hasNext()){
					//System.out.println("best: "+bestInHand);
					Card compareCard = itr.next();
					//System.out.println("compare: "+compareCard);
											
					if(bestInHand.suit().ordinal() == compareCard.suit().ordinal()){
						if (bestInHand.rank().ordinal() < compareCard.rank().ordinal()){
							bestInHand = compareCard;
						}
					} if (trick.getTrumps() != null && bestInHand.suit().ordinal() == trick.getTrumps().ordinal()) {
						break;
					}
					
					if (bestInHand.suit().ordinal() != compareCard.suit().ordinal() ){
						if (bestInHand.rank().ordinal() == compareCard.rank().ordinal()){
							//System.out.println("here");
							if (bestInHand.compareTo(compareCard) == -1){
							bestInHand = compareCard;
							//System.out.println("valHere: "+bestInHand);
							}
						}
						
						if (bestInHand.rank().ordinal() < compareCard.rank().ordinal()){
							bestInHand = compareCard;
						}
					}
				}
				return bestInHand;
			}
			
		}
		
		
		
		return null;
	}	
}
