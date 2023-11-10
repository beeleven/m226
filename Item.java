import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item extends Actor
{
    
    private GreenfootImage item = new GreenfootImage("button-purple.png");
    private Random random = new Random();
    
    /**
     * Initialisiert das Item
     */
    public Item()
    {
        
    }
    
    public void act(){
        
        checkCollision();
    }
    
    public void checkCollision() {
        if (isTouching(Rocket.class) || isTouching(Rocket2.class)){
            int randomEffect = random.nextInt(4) + 1;
            
            applyEffectToRocket(randomEffect);
            
            getWorld().removeObject(this);
        }
    }
    
    private void applyEffectToRocket(int effect) {
        if (isTouching(Rocket.class)) {
            Rocket rocket = (Rocket) getOneIntersectingObject(Rocket.class);
            rocket.applyEffect(effect);
        } else if (isTouching(Rocket2.class)) {
            Rocket2 rocket2 = (Rocket2) getOneIntersectingObject(Rocket2.class);
            rocket2.applyEffect(effect);
        }
    }
    
}
