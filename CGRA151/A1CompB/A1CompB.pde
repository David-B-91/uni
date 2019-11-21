size(400,400);
for (int i=0; i<500 ;i++) {
  float xVal = random (0,400);
  float yVal = random (0,400);
  triangle(xVal+random(-30,30),yVal+random(-30,30),
            xVal+random(-30,30),yVal+random(-30,30),
            xVal+random(-30,30),yVal+random(-30,30));
}
