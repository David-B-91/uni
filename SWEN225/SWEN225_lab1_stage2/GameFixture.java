/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/



// line 31 "model.ump"
// line 57 "model.ump"
public class GameFixture
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameFixture Attributes
  private String fixtureDescription;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameFixture(String aFixtureDescription)
  {
    fixtureDescription = aFixtureDescription;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFixtureDescription(String aFixtureDescription)
  {
    boolean wasSet = false;
    fixtureDescription = aFixtureDescription;
    wasSet = true;
    return wasSet;
  }

  public String getFixtureDescription()
  {
    return fixtureDescription;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "fixtureDescription" + ":" + getFixtureDescription()+ "]";
  }
}