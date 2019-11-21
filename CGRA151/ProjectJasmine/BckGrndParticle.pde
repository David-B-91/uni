class BckGrndParticle extends GameObject {
 
  /*Constructor*/
  BckGrndParticle() {
   x = random(0,width);
   y = 0;
   dx = 0;
   dy = random(5,7);
  }
  
  void draw () {
    fill(random(100,255)); //twinkle/flicker
    rect(x,y,dy/1.5,dy/1.5);
  }
  
  void action() {
    x+=dx;
    y+=dy;
  }
  
  boolean canRemove(){
    return y>height; 
  }
}
