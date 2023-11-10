import greenfoot.*;  // (World, Actor, GreenfootImage und Greenfoot)
import java.util.*;

/**
 * Eine Rakete, die mit den Pfeiltasten gesteuert werden kann: hoch, runter, links, rechts. 
 * Durch Drücken der Leerzeichentaste wird ein Schuss abgefeuert. 
 * 
 * @author Poul Henriksen
 * @author Michael Kölling
 * @author KEL
 * 
 * @version 2.0
 * @version 2.1
 * Parameter verändern
 */
public class Rocket2 extends Mover
{
    private Vector acceleration;            // Geschwindigkeit der Rakete bei eingeschaltetem Turbo.
    
    private GreenfootImage rocketTwo = new GreenfootImage("rocketTwo.png");
    private GreenfootImage rocketTwoWithThrust = new GreenfootImage("rocketTwoWithThrust.png");

    /**
     * Initialisiert diese Rakete.
     * KEL 
     */
    public Rocket2()
    {
        acceleration = new Vector(0, 0.3);    // zur Beschleunigung, wenn Schubkraft an ist
        setRotation(-180);
        increaseSpeed(new Vector(getRotation(), 0.3));
        

    }

    /**
     * Tut, was eien Rakete so macht. (Das heißt: meistens herumfliegen und wenden,
     * beschleunigen und schießen, wenn die entsprechenden Tasten gedrückt werden.)
     */
    public void act()
    {
        move();
        checkKeys();
        checkCollisionRange();
    }

    
    /**
     * Prüft, ob wir mit einem Asteroiden kollidieren.
     */
    private void checkCollision() 
    {
        Asteroid a = (Asteroid) getOneIntersectingObject(Asteroid.class);
        if (a != null) {
            getWorld().addObject(new Explosion(), getX(), getY());
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Prüft, ob wir mit einem Asteroiden innerhlab des Bereichs kollidieren.
     */
    private void checkCollisionRange() 
    {   
        int range = getImage().getWidth(); //Set radius of the Range
        
        List<Asteroid> asteroids = getObjectsInRange(range, Asteroid.class);
        for (Asteroid a : asteroids) {
            if (a != null) {
                getWorld().addObject(new Explosion(), getX(), getY());
                getWorld().removeObject(this);
            }
        }
    }
    
    /**
     * Prüft, ob irgendeine Taste gedrückt wurde, und reagiert darauf.
     */
    private void checkKeys() 
    {
        ignite(Greenfoot.isKeyDown("w"));
        
        if(Greenfoot.isKeyDown("a")) {
            turn(-5);
        }        
        if(Greenfoot.isKeyDown("d")) {
            turn(5);
        }      
    }
    
    
    /**
     * Soll die Rakete gezündet werden?
     */
    private void ignite(boolean boosterOn) 
    {
        if (boosterOn) {
            setImage(rocketTwoWithThrust);
            acceleration.setDirection(getRotation());
            increaseSpeed(acceleration);
        }
        else {
            setImage(rocketTwo);        
        }
    }
    
     public void applyEffect(int effect){
        switch (effect) {
            case 1:
                setImage("rocket.png");
                break;  
                
            case 2:
                setImage("rocket.png");
                break;
        }
    }

}
