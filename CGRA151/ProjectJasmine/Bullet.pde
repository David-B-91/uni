class Bullet extends GameObject {
     
    Bullet() {
      x = pShip.x;
      y = pShip.y;
      w = 5;
      h = 5;
      dx = 0;
      dy = -5;
      hp = 1;
      hbx = x;
      hby = y;
      hbw = 5;
      hbh = 5;
    }
    
    Bullet(float x, float y, float dx, float dy, float size, float hp) {
      this.x = x;
      this.y = y;
      this.w = size;
      this.h = size;
      this.dx = dx;
      this.dy = dy;
      this.hp = hp;
      hbx = x;
      hby = y;
      hbw = size;
      hbh = size;
    }
    
    void draw(){
       fill (aquaGreen);
       if (this instanceof EnemyBullet) fill(badRed);
       ellipse (x,y,w,h);
    }
    
    void action() {
       x += dx;
       y += dy;
       hbx += dx;
       hby += dy;
    }
    
    boolean canRemove() {
       return y < 0 || hp <= 0;
    }
}

class EnemyBullet extends Bullet {
  
   EnemyBullet(float x, float y, float dx, float dy, float size, float hp){
    super(x, y, dx, dy, size, hp);
    this.w = size*1.5;
    this.h = size*1.5;
  }
}
