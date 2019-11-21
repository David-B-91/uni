size(400,400);
float xVal = 0;
float yVal = 0;
for (int i=0; i<50 ;i++) {
  for (int j=0; j<50;j++) {
      float length = random(2,20);
      rect(xVal,yVal,length,10);
      xVal = xVal + length +random(2,15);
  }
  xVal=0;
yVal= yVal+10;
  float length = random(2,20);
      rect(xVal,yVal,length,10);
      xVal = xVal + length +random(2,15);
}
