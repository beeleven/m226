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
    
    int newWidth = 50;
    int newHeight = 50;
    private GreenfootImage itemRaw = new GreenfootImage("button-purple.png");
    private Random random = new Random();
    int randomEffect;
    
    /**
     * Initialisiert das Item
     */
    public Item()
    {
        
    }
    
    public void act(){
        
        checkCollision();
    }
    
    private int generateRandomNumber() {
        int number;
        do {
            number = random.nextInt(5);
        } while (number == 0);
        return number;
    }

     
    public void checkCollision() {
        if (isTouching(Rocket.class) || isTouching(Rocket2.class)){
            
            randomEffect = generateRandomNumber();
            
            
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
