package swen221.monopoly.testing;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import swen221.monopoly.*;
import swen221.monopoly.locations.Location;
import swen221.monopoly.locations.Property;
import swen221.monopoly.locations.Street;

public class MonopolyTests {
	// this is where you must write your tests; do not alter the package, or the
    // name of this file.  An example test is provided for you.
	
	@Test
	public void testValidBuyProperty_1() {
		// Construct a "mini-game" of Monopoly and with a single player. The
		// player attempts to buy a property. We check that the right amount has
		// been deducted from his/her balance, and that he/she now owns the
		// property and vice-versa.
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game,"Park Lane",1500);
			game.buyProperty(player);
			assertEquals(1150,player.getBalance());
			assertEquals("Park Lane",player.iterator().next().getName());
			Street street = (Street) game.getBoard().findLocation("Park Lane"); 
			assertEquals(player,street.getOwner());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 *  tests for invalid purchases
	 */
	
	@Test
	public void testInvalidBuyProperty_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game,"Go",1500);
			game.buyProperty(player);
			fail("should not allow play to buy this property");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuyProperty_2() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game,"Chance",1500);
			game.buyProperty(player);
			fail("should not allow play to buy this property");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuyProperty_3() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game,"Community Chest",1500);
			game.buyProperty(player);
			fail("should not allow play to buy this property");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuyProperty_4() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Vine Street", 1500);
			Player opp = setupMockOpponent(game, "Vine Street", 1500);
			game.buyProperty(opp);
			assertEquals(1300, opp.getBalance());
			assertEquals("Vine Street", opp.iterator().next().getName());
			game.buyProperty(player);
			fail("should not allow player to buy property owned by another player");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test 
	public void testInvalidBuyProperty_5() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Vine Street", 0);
			game.buyProperty(player);
			fail("should not allow player to buy property with not enough money");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testValidSellProperty_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Vine Street", 1500);
			Location loc = game.getBoard().findLocation("Vine Street");
			game.buyProperty(player);
			game.sellProperty(player, loc);
			assertEquals(false, loc.hasOwner());
			assertEquals(1500,player.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidSellProperty_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Go", 1500);
			Location loc = game.getBoard().findLocation("Go");
			game.sellProperty(player, loc);
			fail("player should not be able to sell a non-property");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidSellProperty_2() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Go", 1500);
			Location loc = game.getBoard().findLocation("Vine Street");
			Player opp = setupMockOpponent(game, "Vine Street", 1500);
			game.buyProperty(opp);
			game.sellProperty(player, loc);
			fail("player should not be able to sell another player's property");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	/**
	 * try to sell mortgaged property
	 */
	@Test
	public void testInvalidSellProperty_3() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Pall Mall", 1500);
			game.buyProperty(player);
			Location loc = game.getBoard().findLocation("Pall Mall");
			game.mortgageProperty(player, loc);
			game.sellProperty(player, loc);
			fail("player should not be able to sell property that is mortgaged");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	/**
	 * test for valid move (1 space)
	 */
	
	@Test
	public void testValidMove_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Go", 1500);
			game.movePlayer(player, 2);
			assertEquals("Community Chest", player.getLocation().getName());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * test valid move past go
	 */
	@Test
	public void testValidMove_2() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Mayfair", 1500);
			game.movePlayer(player, 2);
			assertEquals("Old Kent Road", player.getLocation().getName());
			assertEquals(1700,player.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidMove_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try {
			Player player = setupMockPlayer(game, "Go", 1500);
			game.movePlayer(player, 13);
			fail("shouldnt be able to roll 13");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testValidRent_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Pall Mall", 1500);
			Player opp = setupMockOpponent(game, "Pentonville Road", 1500);
			game.buyProperty(player);
			game.movePlayer(opp, 2);
			assertEquals(1370, player.getBalance());
			assertEquals(1490, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * test that rent is doubled when all of a colour group is owned
	 */
	@Test
	public void testValidRent_2() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 2000);
			Player opp = setupMockOpponent(game, "Mayfair", 1500);
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.movePlayer(opp, 2);
			assertEquals(1884, player.getBalance());
			assertEquals(1696, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * test single station rent
	 */
	@Test
	public void testValidRent_3() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Kings Cross Station", 2000);
			Player opp = setupMockOpponent(game, "Go", 1500);
			game.buyProperty(player);
			game.movePlayer(opp, 5);
			assertEquals(1825, player.getBalance());
			assertEquals(1475, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * test 2 station rent
	 */
	@Test
	public void testValidRent_4() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Kings Cross Station", 2000);
			Player opp = setupMockOpponent(game, "Go", 1500);
			game.buyProperty(player);
			game.movePlayer(player, 6);
			game.movePlayer(player, 4);
			game .buyProperty(player);
			game.movePlayer(opp, 5);
			assertEquals(1650, player.getBalance());
			assertEquals(1450, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * One utility rent test
	 */
	@Test
	public void testValidRent_5() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Electric Company", 2000);
			Player opp = setupMockOpponent(game, "Jail", 1500);
			game.buyProperty(player);
			game.movePlayer(opp, 2);
			assertEquals(1858, player.getBalance());
			assertEquals(1492, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Two utility rent test
	 */
	@Test
	public void testValidRent_6() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Electric Company", 2000);
			Player opp = setupMockOpponent(game, "Jail", 1500);
			game.buyProperty(player);
			game.movePlayer(player, 6);
			game.movePlayer(player, 6);
			game.movePlayer(player, 4);
			game.buyProperty(player);
			game.movePlayer(opp, 2);
			assertEquals(1716, player.getBalance());
			assertEquals(1484, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * test rent with some houses
	 */
	@Test
	public void testValidRent_7() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 2000);
			Player opp = setupMockOpponent(game, "Mayfair", 1500);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 2);
			game.movePlayer(opp, 2);
			assertEquals(1834, player.getBalance());
			assertEquals(1646, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * test rent with a hotel
	 */
	@Test
	public void testValidRent_8() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 2000);
			Player opp = setupMockOpponent(game, "Mayfair", 1500);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 5);
			game.buildHotel(player, loc);
			game.movePlayer(opp, 2);
			assertEquals(1784, player.getBalance());
			assertEquals(1496, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidRent_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 2000);
			Player opp = setupMockOpponent(game, "Mayfair", 1500);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.mortgageProperty(player, loc);
			game.movePlayer(opp, 2);
			assertEquals(1970, player.getBalance());
			assertEquals(1700, opp.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testValidMortgage_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Pall Mall", 1500);
			game.buyProperty(player);
			Location loc = game.getBoard().findLocation("Pall Mall");
			game.mortgageProperty(player, loc);
			Property prop = (Property) loc;
			assertEquals(true, prop.isMortgaged());
			assertEquals(1430, player.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidMortgage_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Go", 1500);
			Location loc = game.getBoard().findLocation("Jail");
			game.mortgageProperty(player, loc);
			fail("should not be able to mortage this property");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidMortgage_2() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Go", 1500);
			Player opp = setupMockOpponent(game, "Pall Mall", 1500);
			Location loc = game.getBoard().findLocation("Pall Mall");
			game.buyProperty(opp);
			game.mortgageProperty(player, loc);
			fail("should not be able to mortage a property owned by another player");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidMortgage_3() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Pall Mall", 1500);
			game.buyProperty(player);
			Location loc = game.getBoard().findLocation("Pall Mall");
			game.mortgageProperty(player, loc);
			game.mortgageProperty(player, loc);
			fail("should not be able to mortgage a property if it is already mortgaged");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testValidUnmortgage_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Pall Mall", 1500);
			game.buyProperty(player);
			Location loc = game.getBoard().findLocation("Pall Mall");
			game.mortgageProperty(player, loc);
			Property prop = (Property) loc;
			game.unmortgageProperty(player, loc);
			assertEquals(false, prop.isMortgaged());
			assertEquals(1353,player.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidUnmortgage_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Pall Mall", 1500);
			game.buyProperty(player);
			Location loc = game.getBoard().findLocation("Go");
			game.unmortgageProperty(player, loc);
			fail("should not be able to unmortgage this location");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidUnmortgage_3() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Go", 1500);
			Player opp = setupMockOpponent(game, "Pall Mall", 1500);
			Location loc = game.getBoard().findLocation("Pall Mall");
			game.buyProperty(opp);
			game.mortgageProperty(opp, loc);
			game.unmortgageProperty(player, loc);
			fail("should not be able to unmortage a property owned by another player");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidUnmortgage_4() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Pall Mall", 1500);
			Location loc = game.getBoard().findLocation("Pall Mall");
			game.buyProperty(player);
			game.unmortgageProperty(player, loc);
			fail("should not be able to unmortage a property if its not mortgaged");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidUnmortgage_5() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Pall Mall", 140);
			game.buyProperty(player);
			Location loc = game.getBoard().findLocation("Pall Mall");
			game.mortgageProperty(player, loc);
			game.unmortgageProperty(player, loc);
			fail("should not be able to unmortgage due to funds");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testValidBuildHouses_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 2000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 1);
			Street st = (Street) loc;
			assertEquals(1, st.getHouses());
			assertEquals(1830, player.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidBuildHouses_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 120);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 1);
			fail("player should not be able to build a house with no money");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHouses_2() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 51000);
			Location loc = game.getBoard().findLocation("Chance");
			game.buildHouses(player, loc, 1);
			fail("player should not be able to build a house on this type of location");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHouses_3() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 1000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.mortgageProperty(player, loc);
			game.buildHouses(player, loc, 1);
			fail("player should not be able to build a house on a mortgaged property");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHouses_4() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 1000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.buildHouses(player, loc, 1);
			fail("player should not be able to build a house without owning all propertys in colour group");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHouses_5() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 1000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 6);			
			fail("player should not be able to build too many houses");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHouses_6() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 1000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 5);
			game.buildHotel(player, loc);
			game.buildHouses(player, loc, 1);
			fail("player should not be able to build a house when a hotel exists");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testValidBuildHotel_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 2000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 5);
			game.buildHotel(player, loc);
			Street st = (Street) loc;
			assertEquals(1, st.getHotels());
			assertEquals(0, st.getHouses());
			assertEquals(1580, player.getBalance());
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidBuildHotel_1() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 370);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 5);
			game.buildHotel(player, loc);
			fail("player should not be able to build a hotel with no money");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHotel_2() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 51000);
			Location loc = game.getBoard().findLocation("Free Parking");
			game.buildHotel(player, loc);
			fail("player should not be able to build a hotel on this type of location");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHotel_3() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 1000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.mortgageProperty(player, loc);
			game.buildHotel(player, loc);
			fail("player should not be able to build a hotel on a mortgaged property");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHotel_4() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 1000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.buildHotel(player, loc);
			fail("player should not be able to build a hotel without owning all propertys in colour group");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHotel_5() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 1000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 1);
			game.buildHotel(player, loc);
			fail("player should not be able to build a hotel without 5 houses");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	@Test
	public void testInvalidBuildHotel_6() {
		GameOfMonopoly game = new GameOfMonopoly();
		try{
			Player player = setupMockPlayer(game, "Old Kent Road", 1000);
			Location loc = game.getBoard().findLocation("Old Kent Road");
			game.buyProperty(player);
			game.movePlayer(player, 2);
			game.buyProperty(player);
			game.buildHouses(player, loc, 5);
			game.buildHotel(player, loc);
			game.buildHotel(player, loc);
			fail("player should not be able to build a hotel when a hotel exists");
		} catch (GameOfMonopoly.InvalidMove e) {
			
		}
	}
	
	/**
	 * Setup a mock game of monopoly with a player located at a given location.
	 */
	private Player setupMockPlayer(GameOfMonopoly game, String locationName, int balance)
			throws GameOfMonopoly.InvalidMove {
		Board board = game.getBoard();
		Location location = board.findLocation(locationName);
		return new Player("Dave", Player.Token.ScottishTerrier, balance, location);
	}
	
	private Player setupMockOpponent(GameOfMonopoly game, String locationName, int balance)
			throws GameOfMonopoly.InvalidMove {
		Board board = game.getBoard();
		Location location = board.findLocation(locationName);
		return new Player("David", Player.Token.Battleship, balance, location);
	}
}
