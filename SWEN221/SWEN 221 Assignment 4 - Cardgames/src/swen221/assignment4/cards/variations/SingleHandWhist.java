package swen221.assignment4.cards.variations;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import swen221.assignment4.cards.core.Card;
import swen221.assignment4.cards.core.IllegalMove;
import swen221.assignment4.cards.core.Player;
import swen221.assignment4.cards.core.Trick;
import swen221.assignment4.cards.core.Player.Direction;
import swen221.assignment4.cards.util.AbstractCardGame;

/**
 * An implementation of the "classical" rules of Whist.
 * 
 * @author David J. Pearce
 *
 */
public class SingleHandWhist extends AbstractCardGame implements Serializable {

	public SingleHandWhist() {

	}

	public String getName() {
		return "Classic Whist";
	}
	
	public boolean isGameFinished() {
		for (Player.Direction d : Player.Direction.values()) {
			if (scores.get(d) == 1) {
				return true;
			}
		}
		return false;
	}
	
	public void deal(List<Card> deck) {	
		currentTrick = null;
		for (Player.Direction d : Player.Direction.values()) {
			players.get(d).getHand().clear();
		}
		Player.Direction d = Player.Direction.NORTH;
		for (int i = 0; i < deck.size(); ++i) {
			Card card = deck.get(i);
			players.get(d).getHand().add(card);
			d = d.next();
		}		
	}		
}
