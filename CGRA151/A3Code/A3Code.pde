///////////////////////////////////////
// CGRA151 - font design assignment  //
// framework written by Neil Dodgson //
// Copyright (c)2018 Neil A. Dodgson //
///////////////////////////////////////



///////////////////////////////////////////
// These are the two parameters for the font
// xheight is the height of a lowercase letter 'x'
// and is also the width, height, and diameter of the ball.
// linewidth is the width of the stick in the ball and stick model
// and is also the width of the stroke of the ball.
//
// You can change linewidth to get different-looking styles.
float xheight = 100 ;
float linewidth = 25 ;


void setup(){
  size(600,600);
}

void draw(){
  // clear the display to white
  background(255);
  
  ///////////////////////////////////////////
  // turn the default coordinate system upside down
  // this first command moves (0,0) to the bottom left hand corner
  translate(0,height);
  // and this command makes the y-axis point upwards not downwards
  scale(1,-1);
  ///////////////////////////////////////////
  
  
  ///////////////////////////////////////////
  // you may edit anything from here to the end of the function

  // when submitting your code to the ECS submission system please
  // ensure that you have code here that will allow you to demo
  // to your marker, in your marking session, the code you wrote
  // to generate each of your four screen shots
  
  // move the origin to the centre left of the window so that we can see the whole character
  translate(50,200);
  
  // draw the grid to help design characters
  // comment this out if you do not want to see the grid
  //myGrid();
  
  //*****************************************
  // commands to draw characters go here
  //alphabet();
   alphabetLoop();
  
  // the word "munch"
  // letter_m(); letter_u(); letter_n(); letter_c(); letter_h();

  // the word "bad"
  // letter_b(); letter_a(); letter_d();
  
  //letter_q();  letter_u();  letter_o();  letter_p();
   
   //hamburgerfontjig();
  
  //*****************************************
}

void hamburgerfontjig(){
   pushMatrix();
  translate(50,50);
  scale(0.2,0.2);
  rotate(-PI/2+2);
  letter_h(); letter_a(); letter_m(); letter_b(); letter_u(); letter_r(); letter_g(); letter_e(); letter_r();
  letter_f(); letter_o(); letter_n(); letter_t();
  translate(xheight+linewidth,0);
  letter_j(); letter_i(); letter_g();
  popMatrix(); 
}

void alphabet(){
  translate(0,200);
 pushMatrix();
  scale(0.2,0.2);
  letter_a();  letter_b();  letter_c(); letter_d(); letter_e();  letter_f(); 
 popMatrix();
 pushMatrix();
  translate(0,-50);
  scale(0.2,0.2);
  letter_g();  letter_h();   letter_i(); letter_j();  letter_k();  letter_l();  letter_m();  
 popMatrix();
 pushMatrix();
  translate(0,-100);
  scale(0.2,0.2);
  letter_n();  letter_o();  letter_p();   letter_q();  letter_r();  letter_s();  letter_t(); 
 popMatrix();
 pushMatrix();
  translate(0,-150);
  scale(0.2,0.2);
    letter_u();  letter_v();  letter_w();  letter_x();    letter_y();  letter_z();
 popMatrix();
}

void alphabetLoop(){
   translate(width/2,height/2);
  scale(0.2,0.2);
  letter_a();  
  plusRad();
  letter_b();
  plusRad();
  letter_c();  
  plusRad();
   letter_d();  
  plusRad();
   letter_e();  
  plusRad();
    letter_f();  
  plusRad();
  letter_g();  
  plusRad();
    letter_h();  
  plusRad();
     letter_i();  
  plusRad();
   letter_j();   
  plusRad();
   letter_k();    
  plusRad();
  letter_l();   
  plusRad();
   letter_m();  
   
  plusRad();
  
  letter_n();   
  plusRad();
   letter_o();   
  plusRad();
   letter_p();     
  plusRad();
  letter_q();    
  plusRad();
  letter_r();    
  plusRad();
  letter_s();   
  plusRad();
   letter_t(); 
   
  plusRad();
  
    letter_u();   
  plusRad();
   letter_v();   
  plusRad();
   letter_w();   
  plusRad();
   letter_x();     
  plusRad();
   letter_y();    
  plusRad();
  letter_z();
 
}

void plusRad(){
   rotate(-(2*PI)/26); 
}
////////////////////////////////////////////////////////
// myLine(), myCircle() and myArc() are the only
// drawing functions you can call inside your
// letter definitions - do not change them
// You can also use these four transformation functions:
// rotate(), translate(), scale() and shearX()
// and the pushMatrix() and popMatrix() functions
////////////////////////////////////////////////////////

// draw the "stick" component of the ball and stick
void myLine(){
  noStroke();
  fill(0);
  rect(0,0,linewidth,xheight);
}

// draw the "ball" component of the ball and stick 
void myCircle(){
  myArc(0,TWO_PI);
}

// draw an arc of the ball
void myArc(float start, float end){
  stroke(0);
  noFill();
  strokeWeight(linewidth);
  strokeCap(SQUARE);
  ellipseMode(CENTER);
  arc(xheight/2,xheight/2,xheight-linewidth,xheight-linewidth,start,end);
}

// draw a grid to help when designing characters
void myGrid(){
  stroke(192);
  noFill();
  strokeWeight(1);
  strokeCap(SQUARE);
  line(0,0,width,0);
  line(0,xheight,width,xheight);
  line(0,xheight*1.5,width,xheight*1.5);
  line(0,-xheight/2,width,-xheight/2);
  line(0,-xheight+linewidth,0,xheight*2-linewidth);
  line(xheight,-xheight+linewidth,xheight,xheight*2-linewidth);
}

///////////////////////////////////////////////////
// 
// templates for numerals and letters - you are
// expected to edit these
// but the only functions you may use are:
//   myCircle()
//   myLine()
//   myArc(start,finish)
//   scale(sX,sY)
//   translate(tX,tY)
//   rotate(angle)
//   shearX(angle)
//   pushMatrix()
//   popMatrix()
//
///////////////////////////////////////////////////


///////////////////////////////////////////////////
// numerals for the WORKSHEET 
// 0,1,7
///////////////////////////////////////////////////

void numeral_0(){
  pushMatrix();
    pushMatrix();
      translate(0,xheight/2-0.8);
      scale(1,0.55);
      myLine();
    popMatrix();
    pushMatrix();
      translate(xheight-25,xheight/2-0.8);
      scale(1,0.55);
      myLine();
    popMatrix();
    myArc(PI,2*PI);
    translate(0,xheight/2);
    myArc(0,PI);
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);  
}

void numeral_1(){

  pushMatrix();
    scale(1,1.5);
    myLine();
  popMatrix();

  // translation distance has been adjusted to match the width of the character '1'
  // for the numeral '1', the character has a width of linewidth and this is 
  // followed by a space before the next charcter also of width linewidth 
  translate(linewidth+linewidth,0);
  
}

void numeral_7(){
  pushMatrix();
    translate(0,xheight*1.5);
    rotate(-PI/2);
    myLine();
  popMatrix();
  pushMatrix();  
    shearX(atan2(xheight-linewidth,xheight*1.5-linewidth));
    scale(1,(xheight*1.5-linewidth)/xheight);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}


///////////////////////////////////////////////////
// letters for CORE 
// a,b,c,d,h,l,m,n,o,p,q,u
///////////////////////////////////////////////////

void letter_a(){
  pushMatrix();  
    myCircle();
    translate(xheight-linewidth,0);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}


void letter_b(){
  pushMatrix();  
    myCircle();
   // translate(0,xheight/2);
    scale(1,1.5);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_c(){
  pushMatrix();  
    myArc(7,12);
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_d(){
  pushMatrix();  
    myCircle();
    translate(xheight-linewidth,0);
    scale(1,1.5);
    myLine();
  popMatrix();

  // translation distance has been adjusted to match the width of the character 'd'
  // for the letter 'd', the character has a width of xheight and this is 
  // followed by a space before the next charcter of width linewidth 
  translate(xheight+linewidth,0);
}

void letter_h(){
  pushMatrix();
    myArc(0,PI);
    pushMatrix();
      scale(1,1.5);
      myLine();
    popMatrix();
    pushMatrix();
      translate(xheight-25,0);
      scale(1,0.5);
      myLine();
    popMatrix();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_l(){
  pushMatrix();  
    scale(1,1.5);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(linewidth+linewidth,0);
}

void letter_m(){
  pushMatrix();  
    myArc(0,PI);
    myLine();
  popMatrix();
  pushMatrix();
    translate(xheight/1.3,0);
    myArc(0,PI);
  popMatrix();
  pushMatrix();
    translate(xheight/1.33,0);
    scale(1.08,0.5);
    myLine();
  popMatrix(); 
  pushMatrix();
    translate(xheight*1.52,0);
    scale(1.0,0.5);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(2*xheight,0);
}

void letter_n(){
  pushMatrix();  
    myArc(0,PI);
    myLine();
  popMatrix();
  pushMatrix();
    translate(xheight/1.33,0);
    scale(1,0.5);
    myLine();
  popMatrix(); 

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_o(){
  pushMatrix();  
    myCircle();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_p(){
  myCircle();
  pushMatrix();
    translate(0,-xheight/2);
    scale(1,1.5);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_q(){
  myCircle();
  pushMatrix();
    translate(xheight/1.33,-xheight/2);
    scale(1,1.5);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_u(){
  pushMatrix();  
    myArc(PI,2*PI);
    translate(0,xheight/2.05);
    scale(1,0.51);
    myLine();
  popMatrix();
  pushMatrix();
    translate(xheight/1.33,0);
    myLine();
  popMatrix(); 

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}


///////////////////////////////////////////////////
// letters for COMPLETION 
// e,f,g,i,j,r,t
///////////////////////////////////////////////////

void letter_e(){
  pushMatrix();  
    myArc(0,PI+(PI/2));
  popMatrix();
  pushMatrix();
    rotate(-PI/2);
    translate(-xheight/1.75,0);
    scale(0.5,0.996);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_f(){
  pushMatrix();  
    myLine();
  popMatrix();
  pushMatrix();
    translate(0,xheight*0.5);
    myArc(PI/2,PI);
  popMatrix();
  pushMatrix();
    translate(0,xheight/1.2);
    rotate(-PI/2);
    scale(1,0.5);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(3*linewidth,0);
}

void letter_g(){
    
  pushMatrix();
    myCircle();
    translate(xheight/1.3,xheight*-0.1);
    scale(1,1.1);
    myLine();
  popMatrix();
  pushMatrix();
    translate(xheight/50,-xheight*0.5);
    myArc((-PI)+0.5,0);
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_i(){
  pushMatrix();  
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(linewidth+linewidth,0);
}

void letter_j(){
  pushMatrix();  
    translate(linewidth,xheight*-0.08);
    scale(1,1.08);
    myLine();
  popMatrix();
  pushMatrix();
    translate(-2*linewidth,-xheight*0.5);
    myArc((-PI/2),0);
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(3*linewidth,0);
}

void letter_r(){
  pushMatrix();  
    myLine();
    myArc(1,3);
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight,0);
}

void letter_t(){
  pushMatrix();
    translate(linewidth,0);
    scale(1,1.5);
    myLine();
  popMatrix();
  pushMatrix();
    translate(0,xheight);
    rotate(-PI/2);
    scale(1,0.75);
    myLine();
  popMatrix();
  

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight,0);
}


///////////////////////////////////////////////////
// letters for CHALLENGE 
// k,s,v,w,x,y,z
///////////////////////////////////////////////////

void letter_k(){
  pushMatrix();  
    scale(1,1.5);
    myLine();  
  popMatrix();
  pushMatrix();
    translate(0,xheight/2);
   // shearX(atan2(linewidth,xheight-linewidth));
   shearX(PI/4);
   scale(1,0.5);
    myLine();
  popMatrix();
  pushMatrix();
    translate(0,xheight/2);
   // shearX(atan2(linewidth,xheight-linewidth));
   shearX(-PI/4);
   scale(1,0.5);
   rotate(-PI);
   translate(-linewidth,0);
    myLine();
  popMatrix();
  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight,0);
}

void letter_s(){
  pushMatrix();  
    translate(0,linewidth*1.6);
    scale(1,0.6);
    myArc(0.5,5.1);
  popMatrix();
  pushMatrix();  
   // translate(0,-linewidth);
    scale(1,0.6);
    myArc(3.5,8.2);
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_v(){
   pushMatrix();
   scale(0.75,1);
    pushMatrix();  
      translate(xheight/2,0);
      shearX(atan2(xheight-linewidth,xheight*1.5-linewidth));
      myLine();
    popMatrix();
    pushMatrix();  
      translate(xheight/2,0);
      shearX(atan2(-(xheight-linewidth),(xheight*1.5-linewidth)));
      myLine();
    popMatrix();
  popMatrix();
  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_w(){
  pushMatrix();
    scale(0.5,1);
    pushMatrix();  
      translate(xheight/2,0);
      shearX(atan2(xheight-linewidth,xheight*1.5-linewidth));
      myLine();
    popMatrix();
    pushMatrix();  
      translate(xheight/2,0);
      shearX(atan2(-(xheight-linewidth),(xheight*1.5-linewidth)));
      myLine();
    popMatrix();
  popMatrix();
  
  pushMatrix();
    scale(0.5,1);
    translate(xheight+20,0);
    pushMatrix();  
      translate(xheight/2,0);
      shearX(atan2(xheight-linewidth,xheight*1.5-linewidth));
      myLine();
    popMatrix();
    pushMatrix();  
      translate(xheight/2,0);
      shearX(atan2(-(xheight-linewidth),(xheight*1.5-linewidth)));
      myLine();
    popMatrix();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+2*linewidth,0);
}

void letter_x(){
  pushMatrix();  
    translate(xheight/1.75,0);
    shearX(atan2(-(xheight-linewidth),(xheight*1.5-linewidth)));
    myLine();
  popMatrix();
  pushMatrix();  
   // translate(xheight/2,0);
    shearX(atan2(-(xheight-linewidth),-(xheight*1.5-linewidth)));
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_y(){
   pushMatrix();
   scale(0.75,1);
    pushMatrix();  
      translate(xheight/2,0);
      shearX(atan2(xheight-linewidth,xheight*1.5-linewidth));
      scale(1,-1.5);
      translate(xheight/50,-xheight*0.666);
      myLine();
    popMatrix();
    pushMatrix();  
      translate(xheight/2,0);
      shearX(atan2(-(xheight-linewidth),(xheight*1.5-linewidth)));
      myLine();
    popMatrix();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

void letter_z(){
  pushMatrix();  
    shearX(atan2((xheight-linewidth),(xheight*1.5-linewidth)));
    myLine();
  popMatrix();
  pushMatrix();
    translate(0,xheight);
    rotate(-PI/2);
    scale(1,0.7);
    myLine();
  popMatrix();
  pushMatrix();
    translate(linewidth,linewidth);
    rotate(-PI/2);
    scale(1,0.6);
    myLine();
  popMatrix();

  // adjust the translation distance to match
  // the actual width of your character 
  translate(xheight+linewidth,0);
}

////////////////////////////////////////////////
