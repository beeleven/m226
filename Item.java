import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item extends Actor
{
    
    private GreenfootImage item = new GreenfootImage("button-purple.png");

    
    
    /**
     * Initialisiert das Item
     */
    public Item()
    {
        
    }
    
    public int randomEffect(){
        int number;
        
        number = Greenfoot.getRandomNumber(4);
        
        return number;
        
    }
    
    public void applyLaser(){
        
    }
    
    public void checkCollision() {
    if (isTouching(Rocket.class)) {
        Rocket rocket = (Rocket) getOneIntersectingObject(Rocket.class);
        getWorld().removeObject(this);
    }
}


}
