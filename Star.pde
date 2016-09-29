class Star {
  float x, y, d, fx, fy;
  color c;
  int i=1, j=1;
  int t;
  color colorStar = color(255);
  boolean flash = true;
  color[] colors = new color[] {color(0), color(255), color(211)};
  Star( )
  {
    x = random(0, width); //x position
    y = random(0, height); //y position
    d = random(4,7); //diameter
    fx = 0.0;
    fy=0.0;
  }
  
  float getX(){
   return x; 
  }
  
  void setX(float newX){
    x = newX;
  }
  
  float getY(){
   return y; 
  }
  
  void setY(float newY){
    y = newY;
  }
  
  float getDiameter(){
   return d; 
  }

  
  void setFX(float newFX){
    fx = newFX;
  }
  
  float getFY(){
    return fy;
  }
  
  void setFY(float newFY){
    fy = newFY;
  }
  
  void setColor(color colorS){
    colorStar = colorS;
  }
  
  
  void setFlash(boolean flashOrNah){
   flash = flashOrNah; 
  }
 
 
  void display()
  {
    noStroke();
    if(flash){
      colorStar = colors[(int)random(0, colors.length)];
    }
    fill(colorStar);
    ellipse(x, y, d+5, d);
    ellipse(x, y, d, d+5);
  }
 
}