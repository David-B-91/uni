/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 33 "model.ump"
// line 71 "model.ump"
public class Character extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Character Attributes
  private boolean isMuderer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Character(String aName, boolean aIsMuderer)
  {
    super(aName);
    isMuderer = aIsMuderer;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsMuderer(boolean aIsMuderer)
  {
    boolean wasSet = false;
    isMuderer = aIsMuderer;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsMuderer()
  {
    return isMuderer;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsMuderer()
  {
    return isMuderer;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isMuderer" + ":" + getIsMuderer()+ "]";
  }
}