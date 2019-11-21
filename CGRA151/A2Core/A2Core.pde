float x=100, y=100;
float vx = 3.0, vy = 3.0;
float ballsize = 40;
float batX = 80;
float batY = 20;

void setup() {
  size(400,800);
}

void draw () {
    background (0);
    ellipse (x,y,ballsize,ballsize); //ball
    x += vx; 
    y += vy;
    //change direction if ball contacts left or right side of window.
    if (x > width-ballsize/2 || x < ballsize/2) vx = -vx;
    //change direction if ball contacts top or bottom of window.
    if (y > height-ballsize/2 || y < ballsize/2) vy = -vy;
    
    //bat doesnt go off of left or right of window
    float mx = constrain(mouseX, 0, width-batX);
    //bat doesnt go off top or bottom of window 
    float my = constrain(mouseY, 0, height-batY);
    rect(mx,my,batX,batY); //bat with constraints
    
    
    //boundary of top & bottom of the bat (change direction of ball if top or bottom of bat are hit)
    if (y+ballsize/2 > mouseY && x > mouseX && x < mouseX+batX && y-ballsize/2 < mouseY+batY) vy = -vy;
    //boundary of left and right sides of the bat (change direction of ball if left or right side of bat are hit
    if (x+ballsize/2 > mouseX && y > mouseY && y < mouseY+batY && x-ballsize/2 < mouseX+batX) vx = -vx;
}
