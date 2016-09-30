import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class StellarCollision extends PApplet {

/*
* Stellar Collisions
* Kathleen Tynan - ktynan3
* Drawing Project 
*
* Instructions:
* Click on a star and drag into another
* to begin explosion. Hold down the mouse 
* to expand the explosion. Releasing the mouse
* will stop the explosion.
* Press b to enhance the collision
* Press s to add more stars
* Press space to remove the stars
* Press i to save an image
* Press r to reset
*
*/


//star variables
ArrayList<Star> stars;
float offsetX =0, offsetY = 0;
Star currentStar = new Star();
Star collisionStar = new Star();
int numberStars = 50;

//space colors
int[] colors = new int[] { color(0,0,205), color(0,0,139), color(0,0,128),
    color(153,50,204), color(139,0,139), color(128,0,128), color(225), color(216,191,216), color(0,255,255)};

int[] boomColors = new int[] {color(255), color(220), color(0), color(255,240,245), color(255,255,240)};

float h;
                           
//booleans
boolean collision = false;
boolean overCircle = false, locked = false;
boolean boom;

//explosion variables
float angle; 
float xPos;
float yPos;
float radius;
float littleAngle;


//keep background
PImage saved;


public void setup()
{
  
  
  stars = new ArrayList();
  background(0);
  
  angle = 10; 
  radius = 25;
  littleAngle = 5;
  h=3;
  boom=false;
  
  saved = get();
   
  for (int i=0;i<numberStars;i++)
  {
    fill(255);
    noStroke();
    Star star = new Star();
    stars.add(star);
  
  }
  
  currentStar = stars.get(0);
  
  
  
  
}
 
 
public void draw()
{
  
  //background picture
  background(saved);
  
  
  detect();
  
  //display stars
  
    for(int i = 0; i<stars.size(); i++){
     stars.get(i).display(); 
    }
  
    
  
   
  if(mousePressed && collision){
    
    drawCollision();
   
    
    for(Star element : stars){
      element.setFlash(false);
      element.setColor(0);
    }
    
    
    
    stars.remove(currentStar);
    stars.remove(collisionStar);
    
    saved = get();
    
    
  } else{
   angle = 25;
   radius = 25;
   littleAngle = 5;
   h=3;
  }

 
}

public void keyPressed(){
  if(key == 's'){
    
    fill(255);
    noStroke();
    Star star = new Star();
    stars.add(star);
    numberStars++;
    println(numberStars);
  }
  
  if(key == ' '){
    stars.removeAll(stars);
  }
  
  if(key == 'i'){
    save("StellarCollision.png");
  }
  
  if(key == 'r'){
    setup();
  }
  
  if(key == 'b'){
    boom=!boom;
  }
}
 
 
/*
*
* detects for the mouse over a star and allows
* to move it when pressed
*
*/
public void detect(){
  for(int i = 0; i< stars.size(); i++){
    if (mouseX > stars.get(i).getX()-stars.get(i).getDiameter()/2 && 
          mouseX < stars.get(i).getX()+stars.get(i).getDiameter()/2 && 
            mouseY > stars.get(i).getY()-stars.get(i).getDiameter()/2 && 
                mouseY < stars.get(i).getY()+stars.get(i).getDiameter()/2) {
      
         
         if(mousePressed){
            currentStar = stars.get(i);
            offsetX = mouseX - currentStar.getX();
            currentStar.setFX(offsetX);
            offsetY = mouseY - currentStar.getY();
            currentStar.setFY(offsetY);
            locked=true;
         }
      

      }
     
     if(locked){
      currentStar.setX(mouseX - offsetX);
      currentStar.setY(mouseY - offsetY);
      collision();
     }

  }
}

public void mouseReleased(){
  locked = false;
  collision = false;
  

  for(Star element : stars){
      element.setColor(255);
      element.setFlash(true);
    }
  
}
 
/*
*
* Check for collision
* if collision then saves collision star and
* sets collision to true for stellar collision
*
*/
public void collision(){
  for(int i = 0; i< stars.size(); i++){
    if(currentStar != stars.get(i)){
      if(currentStar.getX()+(currentStar.getDiameter()/2) > stars.get(i).getX()-(stars.get(i).getDiameter()/2)
          && currentStar.getX()-(currentStar.getDiameter()/2) < stars.get(i).getX()+(stars.get(i).getDiameter()/2)
            && currentStar.getY()+(currentStar.getDiameter()/2) > stars.get(i).getY()-(stars.get(i).getDiameter()/2)
              && currentStar.getY()-(currentStar.getDiameter()/2) < stars.get(i).getY()+(stars.get(i).getDiameter()/2)){
                
        collisionStar= stars.get(i);
        collisionStar.setFlash(false);
        currentStar.setFlash(false);
        collisionStar.setColor(0);
        currentStar.setColor(0);
        collision=true;
        
      }
    }
  }
}

public void drawCollision(){
    translate(collisionStar.getX(), collisionStar.getY());
    
    fill(colors[(int)random(0, colors.length)]);
    stroke(colors[(int)random(0, colors.length)]);
    strokeWeight(random(1,2));
    xPos = .7f*cos(radians(angle)) * radius; // so now we get results between -radius and radius
    yPos = .7f*sin(radians(angle)) * radius;
    line(xPos, yPos, xPos+.5f, yPos+.5f);
    line(xPos, yPos, xPos+1.5f, yPos+1.5f);
    line(xPos, yPos, xPos+1, yPos+1);
    
    if(boom){
      boolean x=true;
      strokeWeight(.5f);
      stroke(boomColors[(int)random(0, boomColors.length)]);
      line(0, 0,.7f*cos(radians(angle*5))*radius, .7f*sin(radians(angle*5))*radius);
    }
    
    noStroke();
    ellipse(random(-xPos,xPos), random(-yPos,yPos), random(.5f,3), random(.5f,3));
    ellipse(random(-xPos,xPos), random(-yPos,yPos), random(.5f,3), random(.5f,3));
    ellipse(random(-xPos,xPos), random(-yPos,yPos), random(.5f,3), random(.5f,3));
    ellipse(random(-xPos,xPos), random(-yPos,yPos), 3, 3);
    
    
    
    fill(255);
    ellipse(.7f*littleAngle*sin(littleAngle), .7f*littleAngle*cos(littleAngle), 2, 2);
    
    littleAngle++;
    angle += random(10, 20);
    radius += random(.5f, 1);
}

 
 
 

 
 
 
class Star {
  float x, y, d, fx, fy;
  int c;
  int i=1, j=1;
  int t;
  int colorStar = color(255);
  boolean flash = true;
  int[] colors = new int[] {color(0), color(255), color(211)};
  Star( )
  {
    x = random(0, width); //x position
    y = random(0, height); //y position
    d = random(4,7); //diameter
    fx = 0.0f;
    fy=0.0f;
  }
  
  public float getX(){
   return x; 
  }
  
  public void setX(float newX){
    x = newX;
  }
  
  public float getY(){
   return y; 
  }
  
  public void setY(float newY){
    y = newY;
  }
  
  public float getDiameter(){
   return d; 
  }

  
  public void setFX(float newFX){
    fx = newFX;
  }
  
  public float getFY(){
    return fy;
  }
  
  public void setFY(float newFY){
    fy = newFY;
  }
  
  public void setColor(int colorS){
    colorStar = colorS;
  }
  
  
  public void setFlash(boolean flashOrNah){
   flash = flashOrNah; 
  }
 
 
  public void display()
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
  public void settings() {  size(500, 500, P2D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "StellarCollision" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
