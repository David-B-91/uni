/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 15 "model.ump"
// line 60 "model.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private Player playerOn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(Player aPlayerOn)
  {
    playerOn = aPlayerOn;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPlayerOn(Player aPlayerOn)
  {
    boolean wasSet = false;
    playerOn = aPlayerOn;
    wasSet = true;
    return wasSet;
  }

  public Player getPlayerOn()
  {
    return playerOn;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playerOn" + "=" + (getPlayerOn() != null ? !getPlayerOn().equals(this)  ? getPlayerOn().toString().replaceAll("  ","    ") : "this" : "null");
  }
}