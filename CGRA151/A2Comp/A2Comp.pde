float x=200, y=600;
float vx = 3.0, vy = 3.0;
float ballsize = 40;
float batX = 80;
float batY = 20;
Brick[] bricks = new Brick[10];
float brickGap = 5;
float brickHeight = 25;

void setup() {
  size(400,800);
 
  //setup bricks
  // add second row if time allows
  float brickX = brickGap;
  float brickY = 100;
  for (int i = 0 ; i < bricks.length; i++) { 
     bricks[i] = new Brick(brickX,brickY,(width/bricks.length)-brickGap,3);
     brickX += (width/bricks.length)-(brickGap/bricks.length);
  }
}

void draw () {
    background (0);
    fill(255,255,255);
    ellipse (x,y,ballsize,ballsize); //ball
    x += vx; 
    y += vy;
    //change direction if ball contacts left or right side of window.
    if (x > width-ballsize/2 || x < ballsize/2) vx = -vx;
    //change direction if ball contacts top or bottom of window.
    if (y > height-ballsize/2 || y < ballsize/2) vy = -vy;
    
    //draw bricks
    for (Brick b : bricks) {
        if (b.status == 3) fill(50,205,50);
        if (b.status == 2) fill(255,255,102);
        if (b.status == 1) fill(220,20,60);
        
        //remove brick if hit 3 times
        if (b.status <= 0) {
          b.x =0;
          b.y=0;
          b.brickWidth=0;
          b.status=0;
        }
        
        rect(b.x,b.y,b.brickWidth,brickHeight);
        
        //brick collison
        if (y+ballsize/2 > b.y && x > b.x && x < b.x+b.brickWidth && y-ballsize/2 < b.y+batY){
          vy = -vy;
          b.status--;
        }
        if (x+ballsize/2 > b.x && y > b.y && y < b.y+brickHeight && x-ballsize/2 < b.x+b.brickWidth) {
          vx = -vx;
          b.status--;
        }
    }
    
    
    fill(255,255,255);
    //bat doesnt go off of left or right of window
    float mx = constrain(mouseX, 0, width-batX);
    //bat doesnt go off top or bottom of window 
    float my = constrain(mouseY, 0, height-batY);
    rect(mx,700,batX,batY); //bat with constraints    //700 could be defined earlier. 
    
    
    //boundary of top & bottom of the bat (change direction of ball if top or bottom of bat are hit)
    if (y+ballsize/2 > 700 && x > mouseX && x < mouseX+batX && y-ballsize/2 < 700+batY) vy = -vy;
    //boundary of left and right sides of the bat (change direction of ball if left or right side of bat are hit
    if (x+ballsize/2 > mouseX && y > 700 && y < 700+batY && x-ballsize/2 < mouseX+batX) vx = -vx;
    
    
    
}

public class Brick{
  float x, y, brickWidth, status;
  Brick (float x, float y, float brickWidth, float status){
    this.x = x;
    this.y = y;
    this.brickWidth = brickWidth;
    this.status = status;
  }
}
