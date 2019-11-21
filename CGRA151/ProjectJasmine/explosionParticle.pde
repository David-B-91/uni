class ExplosionParticle extends GameObject {
  
  float size = 3;
  int colour;
 
  ExplosionParticle(float x,float y) {
     this.x = x;
     this.y = y;
     dx = random(-5,5);
     dy = random(-5,5);
     hp = random(100,255);
     colour = (int) random(1,4);
  }
  
  ExplosionParticle(float x,float y, float size) {
     this.x = x;
     this.y = y;
     dx = random(-5,5);
     dy = random(-5,5);
     this.size=size;
     hp = 255;
     colour = (int) random(1,4);
  }
  
  void draw() {
   if (colour == 1) fill(exYellow,hp);
   if (colour == 2) fill(exOrange,hp);
   if (colour == 3) fill(exRed,hp);
   ellipse(x,y,size,size);
  }
  
  void action(){
    x+=dx;
    y+=dy;
    hp = hp - 3;
  }
  
  boolean canRemove() {
     return hp <= 0; 
  }
}
