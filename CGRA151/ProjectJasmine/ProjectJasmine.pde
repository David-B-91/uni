/*Variables*/

// game state/draw state.
int STATE = 0; 
final int MAIN_MENU = 0;
final int GAME_OVER = 1;
final int GAME = 2;
final int PAUSE = 3;

// current level.
int level = 1; 
final int LEVEL_ONE = 1;
final int LEVEL_TWO = 2;

//booleans for key interaction checks
boolean upKey, downKey, leftKey, rightKey;
boolean spaceKey;

boolean levelComplete = false;
int levelCompleteTimer;
final int DISPLAY_DURATION = 2500; //2.5 seconds

//amount of enemies
int enemyCount =0;

//colours to use for bullets and other particles
color aquaGreen = #00F78A;
color badRed = #E00B0B;
color exYellow = #FBFF3B;
color exOrange = #FF8D00;
color exRed = #FF003C;

//player ship
PlayerShip pShip;

//images for loading
PImage playerShipImg, enemyShipImg, enemyShipImg2, enemyShipImg3;

ArrayList<GameObject> objects = new ArrayList<GameObject>(); //list of all GameObjects on the screen/in the game.
ArrayList<GameObject> enemies = new ArrayList<GameObject>();

/*Setup*/
void setup() {
  size(500,800); 
  imageMode(CENTER);
  rectMode(CENTER);
  frameRate(60);
  noStroke();
  //load the images
  playerShipImg  = loadImage("_playershiplel.png");
  enemyShipImg = loadImage("enemy1.png");
  enemyShipImg2 = loadImage("enemy2.png");
  enemyShipImg3 = loadImage("enemy3.png");
  //create player ship
  pShip = new PlayerShip();
  objects.add(pShip);
  
  //load enemeies
  loadEnemies();
}

/*Draw*/

void draw() {
   switch(STATE) {
     case MAIN_MENU: //draw the main menu
       doMainMenu();
     break;
     case GAME_OVER: //draw the game menu
       doGameOver();
     break;
     case GAME:      //draw the game
       switch(level){
         case LEVEL_ONE: //draw level one.
         levelOne();
         break;
         case LEVEL_TWO: //draw level two;
         levelOne();
         break;
       }
       break;
     case PAUSE:     //draw the pause menu
       textSize(30);
       fill(aquaGreen);
       textAlign(CENTER,CENTER);
       text("Paused", width/2,height/2);
     break;
   }
}

/*MAIN_MENU*/
//implementation for main menu.

void doMainMenu(){
  background(0);
  textSize(30);
  fill(aquaGreen);
  textAlign(CENTER,CENTER);
  text("Press Space to Start", width/2,height/2);
       
  int i = objects.size()-1; //index from the back of the arraylist to avoid complications when removing objects from the list whilst iterating.
  while (i>=0) {
    GameObject obj = objects.get(i);
    obj.draw();
    obj.action();
    if (obj.canRemove()) { 
        objects.remove(i);
    } 
    i--;
  }
  objects.add(new BckGrndParticle());
}

/* GAME OVER */
//implementation for game over screen
void doGameOver() {
  textSize(30);
  fill(badRed);
  textAlign(CENTER,CENTER);
  text("GAME OVER", (width/2),(height/2));
  textSize(15);
  text("Press R to Restart", (width/2),(height/2)+30);
}

/*LEVEL_ONE*/
//implementation for level one
void levelOne() {
  if (levelComplete) {
    println(millis() - levelCompleteTimer);
            textSize(30);
            fill(aquaGreen);
            textAlign(CENTER,CENTER);
            text("Level Complete", width/2,height/2);
            //stop showing message after set time
            if (millis() - levelCompleteTimer > DISPLAY_DURATION) {
              levelComplete = false;
            }
       }
  background(0);
  int i = objects.size()-1; //index from the back of the arraylist to avoid complications when removing objects from the list whilst iterating.
  while (i>=0) {
    GameObject obj = objects.get(i);
    obj.draw();
    obj.action();
    if (obj.canRemove()) { 
        //if object being removed is an enemy reduce the enemy count
        if (obj instanceof EnemyShip) enemyCount--;
        objects.remove(i);
    } 
    i--;
  }
  addEnemies();
  objects.add(new BckGrndParticle());
}
  
  

/*Inputs*/

void keyPressed(){
  if (key == CODED) {
    if (keyCode == UP)    upKey = true;
    if (keyCode == DOWN)  downKey = true;
    if (keyCode == LEFT)  leftKey = true;
    if (keyCode == RIGHT) rightKey = true;
  } else {
    if (key == ' ') {
      spaceKey = true;
      if (STATE == MAIN_MENU) {
         STATE = GAME; 
      }
    }
 if (key == 'p' || key =='P') togglePause();
 if (key == 'r' || key =='R' && STATE == GAME_OVER) restartGame();
  }
}

void keyReleased(){
   if (key == CODED) {
    if (keyCode == UP) upKey = false;
    if (keyCode == DOWN) downKey = false;
    if (keyCode == LEFT) leftKey = false;
    if (keyCode == RIGHT) rightKey = false;
  } else {
    if (key == ' ') spaceKey = false;
  } 
}

/*Functions*/

void addEnemies() {
  switch (level) {
     case LEVEL_ONE:
         if (enemyCount == 0 && enemies.size() != 0) {
           //iterate backwards to avoid index problems
             for (int i = 2; i >= 0 ; i--) {
             objects.add(new EnemyShip(enemies.get(i).x,enemies.get(i).y,enemies.get(i).type));
             enemies.remove(i);
             }
             enemyCount = 3;
         }else if (enemyCount == 0 && enemies.size() <=0) {
              levelComplete = true;
              levelCompleteTimer = millis();
              level = LEVEL_TWO;
              loadEnemies();
         }
      case LEVEL_TWO:
         if (enemyCount == 0 && enemies.size() != 0) {
           //iterate backwards to avoid index problems
             for (int i = 4; i >= 0 ; i--) {
             objects.add(new EnemyShip(enemies.get(i).x,enemies.get(i).y,enemies.get(i).type));
             enemies.remove(i);
             }
             enemyCount = 5;
         }
     
  }
  
  
}

void loadEnemies() {
  switch (level) { 
      case LEVEL_ONE:
        //wave one
        enemies.add(new EnemyShip(0+(width/6),1,1));
        enemies.add(new EnemyShip(width/2,1,1));
        enemies.add(new EnemyShip(width-(width/6),1,1));
        //wave two
        enemies.add(new EnemyShip(0+(width/6),1,1));
        enemies.add(new EnemyShip(width/2,1,2));
        enemies.add(new EnemyShip(width-(width/6),1,1));
        //wave three
        enemies.add(new EnemyShip(0+(width/6),1,2));
        enemies.add(new EnemyShip(width/2,1,1));
        enemies.add(new EnemyShip(width-(width/6),1,2));
        //wave four
        enemies.add(new EnemyShip(0+(width/6),1,2));
        enemies.add(new EnemyShip(width/2,1,2));
        enemies.add(new EnemyShip(width-(width/6),1,2));
        //wave five
        enemies.add(new EnemyShip(0+(width/6),1,2));
        enemies.add(new EnemyShip(width/2,1,3));
        enemies.add(new EnemyShip(width-(width/6),1,2));
        break;
      case LEVEL_TWO:
         //wave one
        enemies.add(new EnemyShip(0+(width/6),0,1));
        enemies.add(new EnemyShip((width/6)*2,0,2));
        enemies.add(new EnemyShip(width/2,0,3));
        enemies.add(new EnemyShip(width-((width/6)*2),0,2));
        enemies.add(new EnemyShip(width-(width/6),0,1));
        //wave two
        enemies.add(new EnemyShip(0+(width/6),0,2));
        enemies.add(new EnemyShip((width/6)*2,0,2));
        enemies.add(new EnemyShip(width/2,0,3));
        enemies.add(new EnemyShip(width-((width/6)*2),0,2));
        enemies.add(new EnemyShip(width-(width/6),0,2));
        //wave three
        enemies.add(new EnemyShip(0+(width/6),0,1));
        enemies.add(new EnemyShip((width/6)*2,0,3));
        enemies.add(new EnemyShip(width/2,0,1));
        enemies.add(new EnemyShip(width-((width/6)*2),0,3));
        enemies.add(new EnemyShip(width-(width/6),0,1));
        //wave four
        enemies.add(new EnemyShip(0+(width/6),0,3));
        enemies.add(new EnemyShip((width/6)*2,0,1));
        enemies.add(new EnemyShip(width/2,0,3));
        enemies.add(new EnemyShip(width-((width/6)*2),0,1));
        enemies.add(new EnemyShip(width-(width/6),0,3));
        //wave five
        enemies.add(new EnemyShip(0+(width/6),0,2));
        enemies.add(new EnemyShip((width/6)*2,0,3));
        enemies.add(new EnemyShip(width/2,0,2));
        enemies.add(new EnemyShip(width-((width/6)*2),0,3));
        enemies.add(new EnemyShip(width-(width/6),0,2));
        break;
  }
  
}

// checks collision between bullets and hitboxes
//float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2
boolean checkCollision(GameObject g1, GameObject g2) {
    return (g1.hbx + g1.hbw >= g2.hbx && // 1 right edge past 2 left
            g1.hbx <= g2.hbx + g2.hbw && // 1 left edge past 2 right
            g1.hby + g1.hbh >= g2.hby && // 1 top edge past 2 bottom
            g1.hby <= g2.hby + g2.hbh); // 1 bottom edge past 2 top       
}


void togglePause() {
   switch (STATE){
     case GAME:
       STATE = PAUSE;
       break;
     case PAUSE:
       STATE = GAME;
       break;
   }
}

void restartGame() {
  println("restartGame");
  STATE = GAME;
  level = LEVEL_ONE;
  //objects.clear();//this needs to happen to remove last remaining ship(s) off scrren before reseting enemies. weird fucntionality bugs
  pShip = new PlayerShip();
  objects.add(pShip);
  enemies.clear();
  loadEnemies();
}
