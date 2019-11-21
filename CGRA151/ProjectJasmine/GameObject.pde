abstract class GameObject {
  
  float x,  y; //coordinates of object on screen
  float dx, dy; //speed of obejcts in the 2 possible directions.
  float w, h; //width and height
  int type; //type of enemy
  float hp; //hit points for things that need it
  float hbx, hby, hbw, hbh; //parameters of hitbox
  
  GameObject() {
  }
  
  // classes that extend GameObject will have a draw method to draw that type of object on screen.
  void draw(){
  }
  
  // classes that extend GameObject will have the logic for each Object depending on type
  void action() {
  }
  
  //conditions to remove Objects from the screen/game will be checked here.
  boolean canRemove(){
    return false;
  }
  
}
