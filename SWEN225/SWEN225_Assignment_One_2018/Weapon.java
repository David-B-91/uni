/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 39 "model.ump"
// line 76 "model.ump"
public class Weapon extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Weapon Attributes
  private boolean isMurderWeapon;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(String aName, boolean aIsMurderWeapon)
  {
    super(aName);
    isMurderWeapon = aIsMurderWeapon;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsMurderWeapon(boolean aIsMurderWeapon)
  {
    boolean wasSet = false;
    isMurderWeapon = aIsMurderWeapon;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsMurderWeapon()
  {
    return isMurderWeapon;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsMurderWeapon()
  {
    return isMurderWeapon;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isMurderWeapon" + ":" + getIsMurderWeapon()+ "]";
  }
}