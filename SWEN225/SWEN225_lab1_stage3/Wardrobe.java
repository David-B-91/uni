/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/



// line 63 "model.ump"
// line 109 "model.ump"
public class Wardrobe extends GameFixture
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wardrobe Attributes
  private boolean locked;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wardrobe(String aFixtureDescription)
  {
    super(aFixtureDescription);
    locked = true;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLocked(boolean aLocked)
  {
    boolean wasSet = false;
    locked = aLocked;
    wasSet = true;
    return wasSet;
  }

  public boolean getLocked()
  {
    return locked;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isLocked()
  {
    return locked;
  }

  public void delete()
  {
    super.delete();
  }

  // line 69 "model.ump"
   public String getExtraFixtureDescription(){
    return " The wardrobe door is " + doorDescription();
  }

  // line 73 "model.ump"
   private String doorDescription(){
    if (!locked) return "unlocked.";
     else return "locked.";
  }


  public String toString()
  {
    return super.toString() + "["+
            "locked" + ":" + getLocked()+ "]";
  }
}