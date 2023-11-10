import greenfoot.*;  // (World, Actor, GreenfootImage und Greenfoot)

import java.util.*;

/**
 * Eine Explosion. Sie dehnt sich zuerst aus und fällt dann in sich zusammen. 
 * Die Explosion zerstört andere Objekte, die von der Explosion getroffen werden.
 * 
 * @author Poul Henriksen
 * @author Michael Kölling
 * @version 1.1
 */
public class Explosion extends Actor
{
    /** Wie viele Bilder sollen für die Animation der Explosion verwendet werden. */
    private final static int IMAGE_COUNT= 8;
    
    /** 
     * Die Bilder für die Explosion. Dieses Feld ist static, damit die Bilder nicht für jedes  
     * Objekt neu erzeugt werden müssen (verbessert die Leistung erheblich).
     */
    private static GreenfootImage[] images;
    
    /** Aktuelle Größe der Explosion */
    private int imageNo = 0;
    
    /** Um wie viel inkrementieren wir den Index in der Explosionsanimation. */
    private int increment=1;
    
    /**
     * Erzeugt eine Explosion.
     */
    public Explosion() {
        initialiseImages();
        setImage(images[0]);        
        Greenfoot.playSound("Explosion.wav");
    }    
    
    /** 
     * Erzeugt die Bilder für die Explosion.
     */
    public synchronized static void initialiseImages() 
    {
        if (images == null) {
            GreenfootImage baseImage = new GreenfootImage("explosion-big.png");
            int maxSize = baseImage.getWidth();
            int delta = maxSize / IMAGE_COUNT;
            int size = 0;
            images = new GreenfootImage[IMAGE_COUNT];
            for (int i=0; i < IMAGE_COUNT; i++) {
                size = size + delta;
                images[i] = new GreenfootImage(baseImage);
                images[i].scale(size, size);
            }
        }
    }
    
    /**
     * EXPLOSION!
     */
    public void act()
    { 
        setImage(images[imageNo]);

        imageNo += increment;
        if (imageNo >= IMAGE_COUNT) {
            increment = -increment;
            imageNo += increment;
        }
        
        if (imageNo < 0) {
            getWorld().removeObject(this);
        }
    }
}
