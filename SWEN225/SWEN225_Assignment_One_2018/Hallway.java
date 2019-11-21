/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/


import java.util.*;

// line 53 "model.ump"
// line 92 "model.ump"
public class Hallway extends Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Hallway Associations
  private List<Square> coordinates;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Hallway(Player aPlayerOn)
  {
    super(aPlayerOn);
    coordinates = new ArrayList<Square>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Square getCoordinate(int index)
  {
    Square aCoordinate = coordinates.get(index);
    return aCoordinate;
  }

  public List<Square> getCoordinates()
  {
    List<Square> newCoordinates = Collections.unmodifiableList(coordinates);
    return newCoordinates;
  }

  public int numberOfCoordinates()
  {
    int number = coordinates.size();
    return number;
  }

  public boolean hasCoordinates()
  {
    boolean has = coordinates.size() > 0;
    return has;
  }

  public int indexOfCoordinate(Square aCoordinate)
  {
    int index = coordinates.indexOf(aCoordinate);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCoordinates()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCoordinate(Square aCoordinate)
  {
    boolean wasAdded = false;
    if (coordinates.contains(aCoordinate)) { return false; }
    coordinates.add(aCoordinate);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCoordinate(Square aCoordinate)
  {
    boolean wasRemoved = false;
    if (coordinates.contains(aCoordinate))
    {
      coordinates.remove(aCoordinate);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCoordinateAt(Square aCoordinate, int index)
  {  
    boolean wasAdded = false;
    if(addCoordinate(aCoordinate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCoordinates()) { index = numberOfCoordinates() - 1; }
      coordinates.remove(aCoordinate);
      coordinates.add(index, aCoordinate);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCoordinateAt(Square aCoordinate, int index)
  {
    boolean wasAdded = false;
    if(coordinates.contains(aCoordinate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCoordinates()) { index = numberOfCoordinates() - 1; }
      coordinates.remove(aCoordinate);
      coordinates.add(index, aCoordinate);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCoordinateAt(aCoordinate, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    coordinates.clear();
    super.delete();
  }

}