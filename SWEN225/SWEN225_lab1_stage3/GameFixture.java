/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/



// line 34 "model.ump"
// line 88 "model.ump"
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

  // line 39 "model.ump"
   public String getFullFixtureDescription(){
    StringBuilder fixDes = new StringBuilder();
    fixDes.append(getFixtureDescription());
    fixDes.append(getExtraFixtureDescription());
    return fixDes.toString();
  }

  // line 46 "model.ump"
   public String getExtraFixtureDescription(){
    return "";
  }


  public String toString()
  {
    return super.toString() + "["+
            "fixtureDescription" + ":" + getFixtureDescription()+ "]";
  }
}