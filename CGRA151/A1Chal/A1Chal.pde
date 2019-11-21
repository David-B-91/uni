PGraphics pg;

void setup() {
  size(400,300);
  pg = createGraphics(400,300);
}
void draw() {
  
  float xVal=mouseX;
  float yVal=mouseY;
  noStroke();
  
  //trying to display only a blackcircle without seeing its previous path
  //but retaining all the multicoloured triangles behind it in its old path.
  if(mousePressed) {
    pg.beginDraw();
    pg.background(0,0);
    pg.fill(0,0,0);
    pg.stroke(255);
    pg.ellipse(mouseX,mouseY,50,50);
    pg.endDraw();
    image(pg,0,0);
    fill (random(0,255),random(0,255),random(0,255));
  }
  if (mousePressed)  yVal = yVal - random(-50,-150);
  triangle(xVal,yVal,random(0,width),height,random(0,width),height);
  yVal = mouseY;
  
  if (mousePressed)  xVal = xVal - random(50,150);
  triangle(xVal,yVal,0,random(0,height),0,random(0,width));
  xVal = mouseX;
   
  if (mousePressed)  xVal = xVal - random(-50,-150);
  triangle(xVal,yVal,width,random(0,height),width,random(0,height));
  xVal = mouseX;
  
  if (mousePressed)  yVal = yVal - random(50,150);
  triangle(xVal,yVal,random(0,width),0,random(0,width),0);
  yVal = mouseY;
}

void drawBlackCircle() {
  fill(0,0,0);
  ellipse(mouseX,mouseY,50,50);
}
