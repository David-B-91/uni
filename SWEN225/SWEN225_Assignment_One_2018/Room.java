/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/


import java.util.*;

// line 45 "model.ump"
// line 81 "model.ump"
public class Room extends Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private boolean isMurderLocation;
  private String name;

  //Room Associations
  private List<Square> coordinates;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(Player aPlayerOn, boolean aIsMurderLocation, String aName)
  {
    super(aPlayerOn);
    isMurderLocation = aIsMurderLocation;
    name = aName;
    coordinates = new ArrayList<Square>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsMurderLocation(boolean aIsMurderLocation)
  {
    boolean wasSet = false;
    isMurderLocation = aIsMurderLocation;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsMurderLocation()
  {
    return isMurderLocation;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsMurderLocation()
  {
    return isMurderLocation;
  }
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


  public String toString()
  {
    return super.toString() + "["+
            "isMurderLocation" + ":" + getIsMurderLocation()+ "," +
            "name" + ":" + getName()+ "]";
  }
}