size(400,300);
background(255);

//red rectangle
fill(255,0,0);
rect(40,40,80,40);
//green oval
fill(0,255,0);
ellipse(80,120,80,40);
//blue triangle
fill(0,0,255);
triangle(40,200,80,220,120,160);

//purple polygon
fill(112,0,245);
beginShape();
vertex(200,40);
vertex(220,30);
vertex(280,50);
vertex(260,120);
vertex(218,80);
endShape(CLOSE);

//coloured lines
stroke(111,111,47);
line(200,220,220,160);
stroke(47,111,111);
strokeWeight(2);
line(220,220,240,160);
stroke(111,47,111);
strokeWeight(4);
line(240,220,260,160);
