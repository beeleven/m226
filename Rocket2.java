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
    
    public GreenfootImage rocketTwo = new GreenfootImage("rocketTwo.png");
    private GreenfootImage rocketTwoWithThrust = new GreenfootImage("rocketTwoWithThrust.png");
    private GreenfootImage rocketTwoWithThrustLaser = new GreenfootImage("rocketTwoWithThrustLaser.png");
    private GreenfootImage rocketTwoWithLaser = new GreenfootImage("rocketTwoWithLaser.png");
    private GreenfootImage rocketTwoWithThrustPflug = new GreenfootImage("rocketTwoWithThrustPflug.png");
    private GreenfootImage rocketTwoWithPflug = new GreenfootImage("rocketTwoWithPflug.png");
    private GreenfootImage rocketTwoWithShield = new GreenfootImage("rocketTwoWithShield.png");
    private GreenfootImage rocketTwoWithThrustShield = new GreenfootImage("rocketTwoWithThrustShield.png");
    
    
    private GreenfootImage currentImageNoThrust = rocketTwo;
    private GreenfootImage currentImageWithThrust;
    private boolean hasSpecialEffect = false;
    private Laser laser = null;
    private boolean shieldActive = false;
    private int effectTimer = 0;
    private static final int EFFECT_DURATION = 2 * 60;  // 7 seconds at 60 frames per second
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
        // Check if the countdown is still active in the Space class
        if (((Space) getWorld()).isCountdownActive()) {
            // If countdown is active, don't process rocket control
            return;
        }
        
        move();
        checkKeys();
        if (currentImageWithThrust == rocketTwoWithThrustPflug || currentImageNoThrust == rocketTwoWithPflug) {
            checkCollisionWithEnemy();
        }
        
        if (effectTimer > 0) {
            effectTimer--;
            if (effectTimer == 0) {
                // Effect duration expired, reset the effect
                resetSpecialEffect();
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
        
        if (boosterOn) {
            setImage(rocketTwoWithThrust);
            
            acceleration.setDirection(getRotation());
            increaseSpeed(acceleration);
            
            if(hasSpecialEffect){
                setImage(currentImageWithThrust);
            }
            
        }
        else{
            if(hasSpecialEffect){
                setImage(currentImageNoThrust); 
            }
            else{
                setImage(rocketTwo);
            }
            
        }
    }
    
     public void applyEffect(int effect){
        switch (effect) {
            case 1:
                currentImageWithThrust = (rocketTwoWithThrust);
                currentImageNoThrust = (rocketTwo);
                hasSpecialEffect = true;
                effectTimer = EFFECT_DURATION;
                createLaser(this);
                break;
                
            case 2:
                currentImageWithThrust = (rocketTwoWithThrustPflug);
                currentImageNoThrust = (rocketTwoWithPflug);
                hasSpecialEffect = true;
                effectTimer = EFFECT_DURATION;
                checkCollisionWithEnemy();
                break;
                
            case 3:
                currentImageWithThrust = (rocketTwoWithThrustShield);
                currentImageNoThrust = (rocketTwoWithShield);
                hasSpecialEffect = true;
                effectTimer = EFFECT_DURATION;
                shieldActive = true;
                break;
                
            case 4:
                currentImageWithThrust = (rocketTwoWithThrust);
                currentImageNoThrust = (rocketTwo);
                hasSpecialEffect = true;
                effectTimer = EFFECT_DURATION;
                spawnAsteroids();
                break;
        }
    }
    
    private void createLaser(Actor rocketTwo) {
    // Create a new Laser instance in front of the rocketTwo
    double x = rocketTwo.getX() + Math.cos(Math.toRadians(rocketTwo.getRotation())) * rocketTwo.getImage().getWidth() / 2;
    double y = rocketTwo.getY() + Math.sin(Math.toRadians(rocketTwo.getRotation())) * rocketTwo.getImage().getHeight() / 2;
    laser = new Laser(rocketTwo); // Pass the rocketTwo reference to the Laser constructor
    getWorld().addObject(laser, (int) x, (int) y);
    laser.setRotation(rocketTwo.getRotation());
    }

    private void checkCollisionWithEnemy()
        {
            Rocket enemyRocket = (Rocket) getOneIntersectingObject(Rocket.class);
            
            if(enemyRocket != null){
                if (enemyRocket.hasShield()){
                    return;
                }
                else {
                getWorld().removeObject(enemyRocket);
                }
            }
            
            
        }
    
    public boolean hasShield(){
        return shieldActive;
    }
    
     private void spawnAsteroids() {
    for (int i = 0; i < 3; i++) {
        int size = Greenfoot.getRandomNumber(64) + 32; // Random size between 32 and 95
        Vector speed = new Vector(Greenfoot.getRandomNumber(360), 2);
        Asteroid asteroid = new Asteroid();
        getWorld().addObject(asteroid, Greenfoot.getRandomNumber(getWorld().getWidth()), Greenfoot.getRandomNumber(getWorld().getHeight()));
    }
    }

  private void resetSpecialEffect() {
        hasSpecialEffect = false;
        shieldActive = false;
        currentImageNoThrust = rocketTwo;
        currentImageWithThrust = rocketTwoWithThrust;
        laser = null;
    }

    

}
