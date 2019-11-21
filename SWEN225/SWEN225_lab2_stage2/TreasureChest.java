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
  public enum ChestState { Locked, Unlocked }
  public enum ChestStateUnlocked { Null, Closed, Opened }
  private ChestState chestState;
  private ChestStateUnlocked chestStateUnlocked;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreasureChest()
  {
    description = "";
    setChestStateUnlocked(ChestStateUnlocked.Null);
    setChestState(ChestState.Locked);
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
    if (chestStateUnlocked != ChestStateUnlocked.Null) { answer += "." + chestStateUnlocked.toString(); }
    return answer;
  }

  public ChestState getChestState()
  {
    return chestState;
  }

  public ChestStateUnlocked getChestStateUnlocked()
  {
    return chestStateUnlocked;
  }

  public boolean unlock()
  {
    boolean wasEventProcessed = false;
    
    ChestState aChestState = chestState;
    switch (aChestState)
    {
      case Locked:
        if (playerHasKey())
        {
          setChestState(ChestState.Unlocked);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean lock()
  {
    boolean wasEventProcessed = false;
    
    ChestStateUnlocked aChestStateUnlocked = chestStateUnlocked;
    switch (aChestStateUnlocked)
    {
      case Closed:
        exitChestState();
        // line 34 "model.ump"
        report ("You lock the chest with your key.");
        setChestState(ChestState.Locked);
        wasEventProcessed = true;
        break;
      case Opened:
        exitChestState();
        // line 41 "model.ump"
        report("Thud! The chest closes with a loud noise.\nYou lock the chest with your key.");
        setChestState(ChestState.Locked);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean open()
  {
    boolean wasEventProcessed = false;
    
    ChestStateUnlocked aChestStateUnlocked = chestStateUnlocked;
    switch (aChestStateUnlocked)
    {
      case Closed:
        exitChestStateUnlocked();
        setChestStateUnlocked(ChestStateUnlocked.Opened);
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
    
    ChestStateUnlocked aChestStateUnlocked = chestStateUnlocked;
    switch (aChestStateUnlocked)
    {
      case Opened:
        exitChestStateUnlocked();
        // line 40 "model.ump"
        report("Thud! The chest closes with a loud noise.\nYou closed the lid of the chest.");
        setChestStateUnlocked(ChestStateUnlocked.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitChestState()
  {
    switch(chestState)
    {
      case Unlocked:
        exitChestStateUnlocked();
        break;
    }
  }

  private void setChestState(ChestState aChestState)
  {
    chestState = aChestState;

    // entry actions and do activities
    switch(chestState)
    {
      case Locked:
        // line 25 "model.ump"
        setDescription("locked");
        break;
      case Unlocked:
        if (chestStateUnlocked == ChestStateUnlocked.Null) { setChestStateUnlocked(ChestStateUnlocked.Closed); }
        break;
    }
  }

  private void exitChestStateUnlocked()
  {
    switch(chestStateUnlocked)
    {
      case Closed:
        setChestStateUnlocked(ChestStateUnlocked.Null);
        break;
      case Opened:
        setChestStateUnlocked(ChestStateUnlocked.Null);
        break;
    }
  }

  private void setChestStateUnlocked(ChestStateUnlocked aChestStateUnlocked)
  {
    chestStateUnlocked = aChestStateUnlocked;
    if (chestState != ChestState.Unlocked && aChestStateUnlocked != ChestStateUnlocked.Null) { setChestState(ChestState.Unlocked); }

    // entry actions and do activities
    switch(chestStateUnlocked)
    {
      case Closed:
        // line 33 "model.ump"
        setDescription("closed");
        break;
      case Opened:
        // line 39 "model.ump"
        setDescription("open"); report("You hear a creaking sound.");
        break;
    }
  }

  public void delete()
  {}

  // line 6 "model.ump"
  public Boolean playerHasKey(){
    return true;
  }

  // line 10 "model.ump"
  public String getExtraDescription(){
    return "The old treasure chest is " + getDescription()+".";
  }

  // line 14 "model.ump"
   static  void report(String message){
    System.out.println(message);
  }

  // line 18 "model.ump"
   static  void scenarioHeader(String message){
    report("\n"+message);
      report("------------------------------".substring(1,message.length()));
  }

  // line 49 "model.ump"
   public static  void main(String [] args){
    Thread.currentThread().setUncaughtExceptionHandler(new UmpleExceptionHandler());
    Thread.setDefaultUncaughtExceptionHandler(new UmpleExceptionHandler());
    TreasureChest chest = new TreasureChest();

    report(chest.getExtraDescription());

    scenarioHeader("Unlock then open:");
    chest.unlock();
    report(chest.getExtraDescription());
      
    chest.open();
    report(chest.getExtraDescription());
      
    scenarioHeader("Lock from opened state:");
    chest.lock();
    report(chest.getExtraDescription());
      
    scenarioHeader("Lock from closed state:");
    chest.unlock();
    chest.open();
    chest.close();
    chest.lock();
    report(chest.getExtraDescription());
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]";
  }
  public static class UmpleExceptionHandler implements Thread.UncaughtExceptionHandler
  {
    public void uncaughtException(Thread t, Throwable e)
    {
      translate(e);
      if(e.getCause()!=null)
      {
        translate(e.getCause());
      }
      e.printStackTrace();
    }
    public void translate(Throwable e)
    {
      java.util.List<StackTraceElement> result = new java.util.ArrayList<StackTraceElement>();
      StackTraceElement[] elements = e.getStackTrace();
      try
      {
        for(StackTraceElement element:elements)
        {
          String className = element.getClassName();
          String methodName = element.getMethodName();
          boolean methodFound = false;
          int index = className.lastIndexOf('.')+1;
          try {
            java.lang.reflect.Method query = this.getClass().getMethod(className.substring(index)+"_"+methodName,new Class[]{});
            UmpleSourceData sourceInformation = (UmpleSourceData)query.invoke(this,new Object[]{});
            for(int i=0;i<sourceInformation.size();++i)
            {
              // To compensate for any offsets caused by injected code we need to loop through the other references to this function
              //  and adjust the start / length of the function.
              int functionStart = sourceInformation.getJavaLine(i) + (("main".equals(methodName))?3:1);
              int functionEnd = functionStart + sourceInformation.getLength(i);
              int afterInjectionLines = 0;
              //  We can leverage the fact that all inject statements are added to the uncaught exception list 
              //   before the functions that they are within
              for (int j = 0; j < i; j++) {
                if (sourceInformation.getJavaLine(j) - 1 >= functionStart &&
                    sourceInformation.getJavaLine(j) - 1 <= functionEnd &&
                    sourceInformation.getJavaLine(j) - 1 <= element.getLineNumber()) {
                    // A before injection, +2 for the comments surrounding the injected code
                    if (sourceInformation.getJavaLine(j) - 1 == functionStart) {
                        functionStart += sourceInformation.getLength(j) + 2;
                        functionEnd += sourceInformation.getLength(j) + 2;
                    } else {
                        // An after injection
                        afterInjectionLines += sourceInformation.getLength(j) + 2;
                        functionEnd += sourceInformation.getLength(j) + 2;
                    }
                }
              }
              int distanceFromStart = element.getLineNumber() - functionStart - afterInjectionLines;
              if(distanceFromStart>=0&&distanceFromStart<=sourceInformation.getLength(i))
              {
                result.add(new StackTraceElement(element.getClassName(),element.getMethodName(),sourceInformation.getFileName(i),sourceInformation.getUmpleLine(i)+distanceFromStart));
                methodFound = true;
                break;
              }
            }
          }
          catch (Exception e2){}
          if(!methodFound)
          {
            result.add(element);
          }
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }
      e.setStackTrace(result.toArray(new StackTraceElement[0]));
    }
  //The following methods Map Java lines back to their original Umple file / line    
    public UmpleSourceData TreasureChest_playerHasKey(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(5).setJavaLines(220).setLengths(1);}
    public UmpleSourceData TreasureChest_unlock(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(25).setJavaLines(77).setLengths(1);}
    public UmpleSourceData TreasureChest_scenarioHeader(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(17).setJavaLines(235).setLengths(2);}
    public UmpleSourceData TreasureChest_setChestState(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(24).setJavaLines(177).setLengths(1);}
    public UmpleSourceData TreasureChest_setChestStateUnlocked(){ return new UmpleSourceData().setFileNames("model.ump","model.ump").setUmpleLines(32, 38).setJavaLines(209, 213).setLengths(1, 1);}
    public UmpleSourceData TreasureChest_getExtraDescription(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(9).setJavaLines(225).setLengths(1);}
    public UmpleSourceData TreasureChest_report(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(13).setJavaLines(230).setLengths(1);}
    public UmpleSourceData TreasureChest_lock(){ return new UmpleSourceData().setFileNames("model.ump","model.ump").setUmpleLines(33, 40).setJavaLines(99, 106).setLengths(1, 1);}
    public UmpleSourceData TreasureChest_main(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(48).setJavaLines(241).setLengths(21);}
    public UmpleSourceData TreasureChest_close(){ return new UmpleSourceData().setFileNames("model.ump").setUmpleLines(39).setJavaLines(146).setLengths(1);}

  }
  public static class UmpleSourceData
  {
    String[] umpleFileNames;
    Integer[] umpleLines;
    Integer[] umpleJavaLines;
    Integer[] umpleLengths;
    
    public UmpleSourceData(){
    }
    public String getFileName(int i){
      return umpleFileNames[i];
    }
    public Integer getUmpleLine(int i){
      return umpleLines[i];
    }
    public Integer getJavaLine(int i){
      return umpleJavaLines[i];
    }
    public Integer getLength(int i){
      return umpleLengths[i];
    }
    public UmpleSourceData setFileNames(String... filenames){
      umpleFileNames = filenames;
      return this;
    }
    public UmpleSourceData setUmpleLines(Integer... umplelines){
      umpleLines = umplelines;
      return this;
    }
    public UmpleSourceData setJavaLines(Integer... javalines){
      umpleJavaLines = javalines;
      return this;
    }
    public UmpleSourceData setLengths(Integer... lengths){
      umpleLengths = lengths;
      return this;
    }
    public int size(){
      return umpleFileNames.length;
    }
  }
}