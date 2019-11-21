package swen221.assignment4.cards.util;

import java.io.Serializable;

import swen221.assignment4.cards.core.*;

/**
 * Represents an computer player in the game. This class can be overriden with
 * different implementations that use different kinds of A.I. to determine
 * appropriate moves.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class AbstractComputerPlayer implements Serializable{
	protected Player player;
	
    public AbstractComputerPlayer(Player player) {
    	this.player = player;
    }

	abstract public Card getNextCard(Trick trick);
	
	public void setPlayer(Player player) {
		this.player = player;
	}
}
