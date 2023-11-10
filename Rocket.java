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
public class Rocket extends Mover
{
    private Vector acceleration;            // Geschwindigkeit der Rakete bei eingeschaltetem Turbo.
    
    public GreenfootImage rocket = new GreenfootImage("rocket.png");
    private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    private GreenfootImage rocketWithThrustLaser = new GreenfootImage("rocketWithThrustLaser.png");
    private boolean hasSpecialEffect = false;
    private Laser laser = null;
    

    /**
     * Initialisiert diese Rakete.
     * KEL 
     */
    public Rocket()
    {
        acceleration = new Vector(0, 0.3);    // zur Beschleunigung, wenn Schubkraft an ist
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
        moveLaser();
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
        ignite(Greenfoot.isKeyDown("up"));
        
        if(Greenfoot.isKeyDown("left")) {
            turn(-5);
        }        
        if(Greenfoot.isKeyDown("right")) {
            turn(5);
        }      
    }
    
    
    /**
     * Soll die Rakete gezündet werden?
     */
    private void ignite(boolean boosterOn) 
    {   
        long currentTime = System.currentTimeMillis();
        
        if (boosterOn) {
            
            if (hasSpecialEffect){
                setImage(rocketWithThrustLaser);
            } else {
                setImage(rocketWithThrust);
            }
            
            acceleration.setDirection(getRotation());
            increaseSpeed(acceleration);
        }
        else if(!hasSpecialEffect) {
            setImage(rocket);
        }

    }
    
    public void applyEffect(int effect){
        switch (effect) {
            case 1:
                setImage(rocketWithThrustLaser);
                hasSpecialEffect = false;
                break;
                
            //case 2:
             //   setImage("rocketTwo.png");
             //   hasSpecialEffect = true;
             //   break;
        }
    }
    
    public void attachLaser(){
        laser = new Laser();
        getWorld().addObject(laser, getX(), getY());
        laser.setRotation(getRotation());
        
    }
    
    private void moveLaser() {
        if (laser != null) {
            laser.setLocation(getX(), getY());
            laser.setRotation(getRotation());
        }
    }
}
