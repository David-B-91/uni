abstract class Weapon {
  
  //timer to use in cooldown
  float cooldownTimer;
  //limit to cooldown, when cooldownTimer = cooldownLimit, then weapon is ready to shoot again
  float cooldownLimit;
  
  Weapon(){
  }
  
  void shoot(){
    if (cooldownTimer == cooldownLimit){
      objects.add(new Bullet());
      cooldownTimer = 0;
    }
  }
  
  void cooldown(){
    if (cooldownTimer < cooldownLimit) {
      cooldownTimer++;
    }
  }
  
}
//powerup1
class WeaponBasic extends Weapon {
    
    WeaponBasic() {
      cooldownLimit = 10;
      cooldownTimer = 0;
    }
    
}

class WeaponBasicE extends Weapon {
    GameObject enemy;
    WeaponBasicE(GameObject enemy) {
      this.enemy = enemy;
      cooldownLimit = 25;
      cooldownTimer = 0;
    }
    void shoot(){
      if (cooldownTimer == cooldownLimit && enemy != null){
        EnemyBullet bullet = new EnemyBullet(enemy.x, enemy.y, random(-1,1) ,7,5,1);
        objects.add(bullet);
        cooldownTimer = 0;
      }
    }
    
}
//powerup2
class WeaponV extends Weapon {
  
    WeaponV() {
      cooldownLimit = 10;
      cooldownTimer = 0;
    }
    
    void shoot(){
       if (cooldownTimer == cooldownLimit) {
         objects.add(new Bullet(pShip.x, pShip.y, 2, -13, 5, 2.5));
         objects.add(new Bullet(pShip.x, pShip.y, -2, -13, 5, 2.5));
         cooldownTimer = 0;
       }
    }
  
}

class WeaponVE extends Weapon {
    GameObject enemy;
    WeaponVE(GameObject enemy) {
      this.enemy = enemy;
      cooldownLimit = 25;
      cooldownTimer = 0;
    }
    void shoot(){
      if (cooldownTimer == cooldownLimit && enemy != null){
         objects.add(new EnemyBullet(enemy.x, enemy.y, random(1.7,2.5), 7, 5, 1));
         objects.add(new EnemyBullet(enemy.x, enemy.y, random(-1.7,-2.5), 7, 5, 1));
         cooldownTimer = 0;
      }
    }
    
}
//powerup3
class WeaponDoubleShot extends Weapon {
  
    WeaponDoubleShot() {
      cooldownLimit = 15;
      cooldownTimer = 0;
    }
    
    void shoot(){
       if (cooldownTimer == cooldownLimit) {
         objects.add(new Bullet(pShip.x-(pShip.w/3), pShip.y, 0, -13, 5, 1.5));
         objects.add(new Bullet(pShip.x+(pShip.w/3), pShip.y, 0, -13, 5, 1.5));
         cooldownTimer = 0;
       }
    }
  
}

class WeaponDoubleShotE extends Weapon {
  
  GameObject enemy;
    WeaponDoubleShotE(GameObject enemy) {
      this.enemy = enemy;
      cooldownLimit = 50;
      cooldownTimer = 0;
    }
    
    void shoot(){
       if (cooldownTimer == cooldownLimit) {
         objects.add(new EnemyBullet(enemy.x-(enemy.w/3), enemy.y, random(-1,0), 4, 5, 2));
         objects.add(new EnemyBullet(enemy.x+(enemy.w/3), enemy.y, random(0,1), 4, 5, 2));
         cooldownTimer = 0;
       }
    }
  
}
//powerup4
class WeaponTripleShot extends Weapon {
  
    WeaponTripleShot() {
      cooldownLimit = 20;
      cooldownTimer = 0;
    }
    
    void shoot(){
       if (cooldownTimer == cooldownLimit) {
         objects.add(new Bullet(pShip.x-(pShip.w/3), pShip.y, 0, -13, 5, 1.75));         
         objects.add(new Bullet(pShip.x, pShip.y, 0, -13, 5, 2));
         objects.add(new Bullet(pShip.x+(pShip.w/3), pShip.y, 0, -13, 5, 1.75));
         cooldownTimer = 0;
       }
    }
  
}

class WeaponTripleShotE extends Weapon {
  GameObject enemy;
    WeaponTripleShotE(GameObject enemy) {
      this.enemy = enemy;
      cooldownLimit = 50;
      cooldownTimer = 0;
    }
    
    void shoot(){
       if (cooldownTimer == cooldownLimit) {
         objects.add(new EnemyBullet(enemy.x-(enemy.w/3), enemy.y, random(-2,-1), 4, 5, 1.5));         
         objects.add(new EnemyBullet(enemy.x, enemy.y, random(-0.5,0.5), 4, 5, 1.5));
         objects.add(new EnemyBullet(enemy.x+(enemy.w/3), enemy.y, random(2,1), 4, 5, 1.5));
         cooldownTimer = 0;
       }
    }
  
}
//powerup5
class WeaponQuad extends Weapon {
  
    WeaponQuad() {
      cooldownLimit = 50;
      cooldownTimer = 0;
    }
    
    void shoot(){
       if (cooldownTimer == cooldownLimit) {
         objects.add(new Bullet(pShip.x, pShip.y, 1, -13, 5, 3));         
         objects.add(new Bullet(pShip.x, pShip.y, -1, -13, 5, 3));            
         objects.add(new Bullet(pShip.x, pShip.y, 0.3, -13, 5, 3));
         objects.add(new Bullet(pShip.x, pShip.y, -0.3, -13, 5, 3));
         cooldownTimer = 0;
       }
    }
  
}

class WeaponQuadE extends Weapon {
    GameObject enemy;
    WeaponQuadE(GameObject enemy) {
      this.enemy = enemy;
      cooldownLimit = 60;
      cooldownTimer = 0;
    }
    void shoot(){
      if (cooldownTimer == cooldownLimit && enemy != null){
         objects.add(new EnemyBullet(enemy.x, enemy.y, 1, 5, 5, 3));         
         objects.add(new EnemyBullet(enemy.x, enemy.y, -1, 5, 5, 3));            
         objects.add(new EnemyBullet(enemy.x, enemy.y, 0.3, 5, 5, 3));
         objects.add(new EnemyBullet(enemy.x, enemy.y, -0.3, 5, 5, 3));
         cooldownTimer = 0;
      }
    }
    
}
