package swen221.lab6.tests;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runners.MethodSorters;

import swen221.lab6.connect.Game;
import swen221.lab6.connect.Game.Status;
import swen221.lab6.connect.core.Board;
import swen221.lab6.connect.core.Board.Token;
import swen221.lab6.connect.util.Position;

import org.junit.FixMethodOrder;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectTests {

	@Test public void test_01() {
		String output = "|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";

		Board board = new Board();

		assertEquals(output,board.toString());
	}
	
	/**
	 * Black placement durin white turn
	 */
	@Test (expected = IllegalArgumentException.class)
	public void blackDuringWhiteTurnTest() {
		String output = "|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Token token = Token.BLACK;
		Game game = new Game(board);
		game.placeToken(new Position(0,0), token);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
		
	/**
	 * white placement during white turn
	 */
	@Test public void whiteDuringWhiteTurnTest() {
		String output = "|W|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Token token = Token.WHITE;
		Game game = new Game(board);
		game.placeToken(new Position(0,0), token);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void whiteOnBlackTest() {
		String output = "|B|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		board.setSquare(new Position(0,0), Token.BLACK);
		Token token = Token.WHITE;
		Game game = new Game(board);
		game.placeToken(new Position(0,0), token);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void whiteDuringBlackTurnTest() {
		String output = "|W|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Token token = Token.WHITE;
		Game game = new Game(board);
		game.placeToken(new Position(0,0), token);
		game.placeToken(new Position(0,1), token);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
	
	@Test public void blackDuringBlackTurnTest() {
		String output = "|W|B|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Token.WHITE);
		game.placeToken(new Position(1,0), Token.BLACK);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
	
	@Test public void shortHCapture() {
		String output = "|W|_|W|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Token.WHITE);
		game.placeToken(new Position(1,0), Token.BLACK);
		game.placeToken(new Position(2,0), Token.WHITE);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
	
	@Test public void shortHCapture2() {
		String output = "|_|W|_|W|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(1,0), Token.WHITE);
		game.placeToken(new Position(2,0), Token.BLACK);
		game.placeToken(new Position(3,0), Token.WHITE);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
	
	@Test public void longHCapture() {
		String output = "|W|_|_|W|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Token.WHITE);
		game.placeToken(new Position(1,0), Token.BLACK);
		game.placeToken(new Position(3,0), Token.WHITE);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
	
	@Test public void shortVCapture() {
		String output = "|W|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|W|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		board = game.getBoard();
		assertEquals(output,board.toString());
	
	}
	
	@Test public void longVCapture() {
		String output = "|W|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|W|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		game.placeToken(new Position(0,3), Token.WHITE);
		board = game.getBoard();
		assertEquals(output,board.toString());
		
	
	}
	
	@Test public void colWinWhite() {
		String output = "|W|B|_|_|\n" +
						"|W|_|B|_|\n" +
						"|W|_|B|_|\n" +
						"|W|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Token.WHITE);
		game.placeToken(new Position(1,0), Token.BLACK);
		game.placeToken(new Position(0,1), Token.WHITE);
		game.placeToken(new Position(2,1), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(2,2), Token.BLACK);
		game.placeToken(new Position(0,3), Token.WHITE);
		board = game.getBoard();
		assertEquals(Status.WHITEWON,game.getStatus());
		
	
	}
	
	@Test public void rowWinBlack() {
		String output = "|B|B|B|B|\n" +
						"|W|W|_|_|\n" +
						"|_|_|_|_|\n" +
						"|W|_|_|W|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,1), Token.WHITE);
		game.placeToken(new Position(0,0), Token.BLACK);
		game.placeToken(new Position(0,3), Token.WHITE);
		game.placeToken(new Position(1,0), Token.BLACK);
		game.placeToken(new Position(1,1), Token.WHITE);
		game.placeToken(new Position(2,0), Token.BLACK);
		game.placeToken(new Position(3,3), Token.WHITE);
		game.placeToken(new Position(3,0), Token.BLACK);
		board = game.getBoard();
		assertEquals(Status.BLACKWON,game.getStatus());
		
	
	}
	
	@Test public void mutualCapture() {
		String output = "|W|_|_|B|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Token.WHITE);
		game.placeToken(new Position(0,3), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		board = game.getBoard();
		assertEquals(output,board.toString());
		
	
	}
	
	@Test public void Stalemate() {
		String output = "|W|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|_|_|_|_|\n" +
						"|B|_|_|_|\n";
		
		
		Board board = new Board();
		Game game = new Game(board);
		game.placeToken(new Position(0,0), Token.WHITE);
		game.placeToken(new Position(0,3), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		game.placeToken(new Position(0,2), Token.WHITE);
		game.placeToken(new Position(0,1), Token.BLACK);
		board = game.getBoard();
		assertEquals(Status.STALEMATE,game.getStatus());
		
	
	}

}
