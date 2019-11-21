/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/


import java.util.*;

// line 2 "model.ump"
// line 98 "model.ump"
// line 104 "model.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Associations
  private List<Square> board;
  private List<Player> players;
  private List<Card> deck;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(Player... allPlayers)
  {
    board = new ArrayList<Square>();
    players = new ArrayList<Player>();
    boolean didAddPlayers = setPlayers(allPlayers);
    if (!didAddPlayers)
    {
      throw new RuntimeException("Unable to create Game, must have 2 to 6 players");
    }
    deck = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Square getBoard(int index)
  {
    Square aBoard = board.get(index);
    return aBoard;
  }

  public List<Square> getBoard()
  {
    List<Square> newBoard = Collections.unmodifiableList(board);
    return newBoard;
  }

  public int numberOfBoard()
  {
    int number = board.size();
    return number;
  }

  public boolean hasBoard()
  {
    boolean has = board.size() > 0;
    return has;
  }

  public int indexOfBoard(Square aBoard)
  {
    int index = board.indexOf(aBoard);
    return index;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Card getDeck(int index)
  {
    Card aDeck = deck.get(index);
    return aDeck;
  }

  public List<Card> getDeck()
  {
    List<Card> newDeck = Collections.unmodifiableList(deck);
    return newDeck;
  }

  public int numberOfDeck()
  {
    int number = deck.size();
    return number;
  }

  public boolean hasDeck()
  {
    boolean has = deck.size() > 0;
    return has;
  }

  public int indexOfDeck(Card aDeck)
  {
    int index = deck.indexOf(aDeck);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoard()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addBoard(Square aBoard)
  {
    boolean wasAdded = false;
    if (board.contains(aBoard)) { return false; }
    board.add(aBoard);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBoard(Square aBoard)
  {
    boolean wasRemoved = false;
    if (board.contains(aBoard))
    {
      board.remove(aBoard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBoardAt(Square aBoard, int index)
  {  
    boolean wasAdded = false;
    if(addBoard(aBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoard()) { index = numberOfBoard() - 1; }
      board.remove(aBoard);
      board.add(index, aBoard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBoardAt(Square aBoard, int index)
  {
    boolean wasAdded = false;
    if(board.contains(aBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoard()) { index = numberOfBoard() - 1; }
      board.remove(aBoard);
      board.add(index, aBoard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBoardAt(aBoard, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 6;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() < maximumNumberOfPlayers())
    {
      players.add(aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    if (!players.contains(aPlayer))
    {
      return wasRemoved;
    }

    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }

    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setPlayers(Player... newPlayers)
  {
    boolean wasSet = false;
    ArrayList<Player> verifiedPlayers = new ArrayList<Player>();
    for (Player aPlayer : newPlayers)
    {
      if (verifiedPlayers.contains(aPlayer))
      {
        continue;
      }
      verifiedPlayers.add(aPlayer);
    }

    if (verifiedPlayers.size() != newPlayers.length || verifiedPlayers.size() < minimumNumberOfPlayers() || verifiedPlayers.size() > maximumNumberOfPlayers())
    {
      return wasSet;
    }

    players.clear();
    players.addAll(verifiedPlayers);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDeck()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addDeck(Card aDeck)
  {
    boolean wasAdded = false;
    if (deck.contains(aDeck)) { return false; }
    deck.add(aDeck);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDeck(Card aDeck)
  {
    boolean wasRemoved = false;
    if (deck.contains(aDeck))
    {
      deck.remove(aDeck);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDeckAt(Card aDeck, int index)
  {  
    boolean wasAdded = false;
    if(addDeck(aDeck))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDeck()) { index = numberOfDeck() - 1; }
      deck.remove(aDeck);
      deck.add(index, aDeck);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDeckAt(Card aDeck, int index)
  {
    boolean wasAdded = false;
    if(deck.contains(aDeck))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDeck()) { index = numberOfDeck() - 1; }
      deck.remove(aDeck);
      deck.add(index, aDeck);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDeckAt(aDeck, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    board.clear();
    players.clear();
    deck.clear();
  }

}