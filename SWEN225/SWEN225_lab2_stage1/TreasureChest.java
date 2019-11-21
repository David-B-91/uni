/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4160.f573280ad modeling language!*/



// line 2 "model.ump"
public class TreasureChest
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreasureChest Attributes
  private String description;

  //TreasureChest State Machines
  public enum ChestState { Closed, Opened }
  private ChestState chestState;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreasureChest()
  {
    description = "";
    setChestState(ChestState.Closed);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }

  public String getChestStateFullName()
  {
    String answer = chestState.toString();
    return answer;
  }

  public ChestState getChestState()
  {
    return chestState;
  }

  public boolean open()
  {
    boolean wasEventProcessed = false;

    ChestState aChestState = chestState;
    switch (aChestState)
    {
      case Closed:
        setChestState(ChestState.Opened);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean close()
  {
    boolean wasEventProcessed = false;

    ChestState aChestState = chestState;
    switch (aChestState)
    {
      case Opened:
        // line 27 "model.ump"
        report("Thud! The chest clsoes with a loud noise.");
        setChestState(ChestState.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setChestState(ChestState aChestState)
  {
    chestState = aChestState;

    // entry actions and do activities
    switch(chestState)
    {
      case Closed:
        // line 21 "model.ump"
        setDescription("closed"); report ("You closed the lid of the chest.");
        break;
      case Opened:
        // line 26 "model.ump"
        setDescription("open"); report("You hear a creaking sound.");
        break;
    }
  }

  public void delete()
  {}

  // line 6 "model.ump"
  public String getExtraDescription(){
    return "The old treasure chest is " + getDescription()+".";
  }

  // line 10 "model.ump"
   static  void report(String message){
    System.out.println(message);
  }

  // line 14 "model.ump"
   static  void scenarioHeader(String message){
    report("\n"+message);
      report("------------------------------".substring(1,message.length()));
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]";
  }


public static void main (String[] args) {
    TreasureChest chest = new TreasureChest();

    report(chest.getExtraDescription());

    chest.open();
    report(chest.getExtraDescription());

    chest.close();
    report(chest.getExtraDescription());
}

}

