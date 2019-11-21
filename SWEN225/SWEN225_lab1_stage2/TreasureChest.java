/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/



// line 36 "model.ump"
// line 63 "model.ump"
public class TreasureChest extends GameFixture
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreasureChest Attributes
  private boolean open;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreasureChest(String aFixtureDescription)
  {
    super(aFixtureDescription);
    open = false;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpen(boolean aOpen)
  {
    boolean wasSet = false;
    open = aOpen;
    wasSet = true;
    return wasSet;
  }

  public boolean getOpen()
  {
    return open;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isOpen()
  {
    return open;
  }

  public void delete()
  {
    super.delete();
  }

  // line 42 "model.ump"
   public String getFixtureDescription(){
    if (getOpen()) return "A large and heavy treasure chest. It contains the room key!";
    else return "A large and heavy treasure chest. It's lid is closed";
  }


  public String toString()
  {
    return super.toString() + "["+
            "open" + ":" + getOpen()+ "]";
  }
}