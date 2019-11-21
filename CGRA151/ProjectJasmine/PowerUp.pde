class PowerUp extends GameObject {
  
  boolean healthPickup = false;
  
  PowerUp(float x, float y, float dx, float dy){
    this.x = x;
    this.y = y;
    this.w = 15;
    this.h = 15;
    this.hp = 1;
    this.dx = dx;
    this.dy = dy;
    this.hbx = x;
    this.hby = y;
    this.hbw = 15;
    this.hbh = 15;
    isHealth();
  }
  
  void draw() {
    fill(#2F3FE8);
    if (healthPickup) fill(#50FF17);
    ellipse(x,y,w,h);
  }
  
  void action(){
    x+=dx;
    y+=dy;
    hbx+=dx;
    hby+=dy;
  }
  
  boolean canRemove(){
    return y > height || hp == 0;
  }
  
  void isHealth() {
    int healthChance = (int) random (3);
     if (healthChance == 1) healthPickup = true;
  }
  
}
