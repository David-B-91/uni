class EnemyShip extends GameObject {
  
   Weapon enemyWeapon = new WeaponBasicE(this);;
  
   EnemyShip(float x, float y, int type) {
       this.x = x;
       this.y = y;
       this.type = type;
       switch (type){
       case 1:           
           w = 38;
           h = 50;
           hp = 7;
           hbx = x;
           hby = y;
           hbw = 25;
           hbh = 50;
           dx = 0;
           dy = 1;
           enemyWeapon = chooseWeapon();
           break;
       case 2:           
           w = 38;
           h = 38;
           hp = 15;
           hbx = x;
           hby = y;
           hbw = 20;
           hbh = 20;
           dx = 0;
           dy = 0.5;
           enemyWeapon = chooseWeapon();
           break;
        case 3:           
           w = 38;
           h = 38;
           hp = 21;
           hbx = x;
           hby = y;
           hbw = 20;
           hbh = 30;
           dx = 0;
           dy = 1.5;
           enemyWeapon = chooseWeapon();
           break;
         }
   }
   
   void draw() {
     switch(type) {
     case 1:
         image(enemyShipImg,x,y,w,h);
         //display hitbox
         fill(badRed,40);
         rect(hbx,hby,hbw,hbh);
         break;
       case 2:
         image(enemyShipImg2,x,y,w,h);
         //display hitbox
         fill(badRed,40);
         rect(hbx,hby,hbw,hbh);
         break;
       case 3:
         image(enemyShipImg3,x,y,w,h);
         //display hitbox
         fill(badRed,40);
         rect(hbx,hby,hbw,hbh);
         break;
       }
   }
   
   void action() {
     switch (type){
     case 1:
       if (y<height/3) {
         x+=dx;
         y+=dy;
         //update hitbox
         hbx += dx;
         hby += dy;
        }
        break;
     case 2:
        if (y<height/3) {
         x+=dx;
         y+=dy;
         //update hitbox
         hbx += dx;
         hby += dy;
        }
        break;
     case 3:
       if (y<height/3) {
         x+=dx;
         y+=dy;
         //update hitbox
         hbx += dx;
         hby += dy;
        }
        break; 
     }
     
       if (y > 0) {
         enemyWeapon.shoot();
         enemyWeapon.cooldown();
       }
       //checks collisions
       int i = 0;
       while (i < objects.size()) {
         GameObject obj = objects.get(i);
         if (obj instanceof EnemyBullet){
       } else if (obj instanceof Bullet) {
              if (checkCollision(obj,this)) {
              this.hp = this.hp - obj.hp; //bullet does damage equal to bullet hp
              obj.hp = obj.hp-obj.hp; // bullet does damage to itself equal to its own hp.
              //5particles for bullet dying
              for (int j = 0; j < 5; j++) { 
                objects.add(new ExplosionParticle(obj.x,obj.y));
              }//25 particle explosion when ship dies
              if (this.hp == 0) {
                 int pickupDrop = (int) random(4);
                 if (pickupDrop == 1) {
                   objects.add(new PowerUp(this.x, this.y, 0, 3));
                 }
                
                for (int j = 0 ; j<100; j++) {
                  objects.add(new ExplosionParticle(this.x, this.y, 4));
                }}
            }
            }
         i++;
       }
   }
   
   boolean canRemove() {
       return y > height || hp <= 0;
   }
   
   int getType() {
      return this.type; 
   }
   
   Weapon chooseWeapon() {
     switch (type){
       case 1:
         switch (level) {
           case LEVEL_ONE:
             return new WeaponBasicE(this);
           case LEVEL_TWO:
             return new WeaponDoubleShotE(this);
         }
       break;
       case 2:
         switch (level) {
           case LEVEL_ONE:
             return new WeaponVE(this);
           case LEVEL_TWO:
             return new WeaponTripleShotE(this);
         }
       break;
       case 3:
         switch (level) {
           case LEVEL_ONE:
             return new WeaponQuadE(this);
           case LEVEL_TWO:
              return new WeaponQuadE(this);
         }
       break;
     } 
   return new WeaponBasicE(this);
   }
  
}
