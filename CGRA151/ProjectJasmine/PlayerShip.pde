class PlayerShip extends GameObject {
  
  Weapon myWeapon;
  
  PlayerShip() {
    x = width/2;
    y = height/1.1;
    w = 40;
    h = 70;
    dx = 0;
    dy = 0;
    hp = 100;
    hbx = x;
    hby = y;
    hbw = 25;
    hbh = 40;
    myWeapon = new WeaponTripleShot();
  }
  
  void draw() {
    fill(aquaGreen);
    textSize(20);
    textAlign(LEFT,BOTTOM);
    text("Health: "+hp,40,40);
    
    //aspect ratio of ship img is 9:16
    image(playerShipImg, x,y,w,h);
    //display hitbox
  fill(aquaGreen,40);
  rect(hbx,hby,hbw,hbh);
  }
  
  void action() {
    dx =0;
    dy =0;
    
    //maybe have speed be a portion of the screen size
    if (upKey)    dy += -5;
    if (downKey)  dy += 5;
    if (leftKey)  dx += -5;
    if (rightKey) dx += 5;   
    if ((x+w/2) + dx < width && (y+h/2) + dy < height &&
          (x-w/2) + dx > 0 && (y-h/2) + dy > 0) {
    x += dx; hbx += dx; //update x and hitbox x
    y += dy; hby += dy; //update y and hitbox y
    }
    if (spaceKey) myWeapon.shoot(); //objects.add(new Bullet());
    
    myWeapon.cooldown();
    
    //checks collisions
       int i = 0;
       while (i < objects.size()) {
         GameObject obj = objects.get(i);
         if (obj instanceof EnemyBullet) {
            if (checkCollision(obj,this)) {
              this.hp = this.hp - obj.hp; //bullet does damage equal to bullet hp
              obj.hp = obj.hp-obj.hp; // bullet does damage to itself equal to its own hp.
              //5particles for bullet dying
              for (int j = 0; j < 5; j++) { 
                objects.add(new ExplosionParticle(obj.x,obj.y));
              }//25 particle explosion when ship dies
              if (this.hp == 0) {
                for (int j = 0 ; j<100; j++) {
                  objects.add(new ExplosionParticle(this.x, this.y, 4));
                }}
            }
            }else if (obj instanceof EnemyShip) {
               if (checkCollision(obj,this)) {
                  this.hp = this.hp - obj.hp;
                  obj.hp = 0;
                  //25 particle explosion when ship dies
                  for (int j = 0 ; j<100; j++) {
                    objects.add(new ExplosionParticle(this.x, this.y, 4));
                  }
            }
         } else if (obj instanceof PowerUp) {
           PowerUp pUp = (PowerUp) obj;
           if (checkCollision(obj,this)) {
             if (pUp.healthPickup){
               this.hp = this.hp + 10;
             } else {
               int weaponNum = (int) random(5);
               switch (weaponNum){
                    case 0:
                      myWeapon = new WeaponBasic();
                    break;
                    case 1:
                      myWeapon = new WeaponV();
                    break;
                    case 2:
                      myWeapon = new WeaponDoubleShot();
                    break;
                    case 3:
                      myWeapon = new WeaponTripleShot();
                    break;
                    case 4:
                      myWeapon = new WeaponQuad();
                    break;
                 }
               }
             obj.hp = 0;
           }
         }
         i++;
       }
       
       if (this.hp <= 0) {
         STATE = GAME_OVER;
       }
    
  }
  
  boolean canRemove() {
    return hp <= 0;
  }
  
}
