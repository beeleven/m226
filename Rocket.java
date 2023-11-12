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
    private GreenfootImage rocketWithLaser = new GreenfootImage("rocketWithLaser.png");
    private GreenfootImage rocketWithThrustPflug = new GreenfootImage("rocketWithThrustPflug.png");
    private GreenfootImage rocketWithPflug = new GreenfootImage("rocketWithPflug.png");
    private GreenfootImage rocketWithShield = new GreenfootImage("rocketWithShield.png");
    private GreenfootImage rocketWithThrustShield = new GreenfootImage("rocketWithThrustShield.png");
    
    
    private GreenfootImage currentImageNoThrust = rocket;
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
        // Check if the countdown is still active in the Space class
        if (((Space) getWorld()).isCountdownActive()) {
            // If countdown is active, don't process rocket control
            return;
        }
        
        move();
        checkKeys();
        if (currentImageWithThrust == rocketWithThrustPflug || currentImageNoThrust == rocketWithPflug) {
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
            setImage(rocketWithThrust);
            
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
                setImage(rocket);
            }
            
        }
    }
    
    public void applyEffect(int effect){
        switch (effect) {
            case 1:
                currentImageWithThrust = (rocketWithThrustLaser);
                currentImageNoThrust = (rocketWithLaser);
                hasSpecialEffect = true;
                effectTimer = EFFECT_DURATION;
                createLaser(this);
                break;
                
            case 2:
                currentImageWithThrust = (rocketWithThrustPflug);
                currentImageNoThrust = (rocketWithPflug);
                hasSpecialEffect = true;
                effectTimer = EFFECT_DURATION;
                checkCollisionWithEnemy();
                break;
                
            case 3:
                currentImageWithThrust = (rocketWithThrustShield);
                currentImageNoThrust = (rocketWithShield);
                hasSpecialEffect = true;
                effectTimer = EFFECT_DURATION;
                shieldActive = true;
                break;
                
            case 4:
                currentImageWithThrust = (rocketWithThrustShield);
                currentImageNoThrust = (rocketWithShield);
                hasSpecialEffect = true;
                effectTimer = EFFECT_DURATION;
                spawnAsteroids();
                break;
        }
    }
    
    
    private void checkCollisionWithEnemy()
    {
        Rocket2 enemyRocket = (Rocket2) getOneIntersectingObject(Rocket2.class);
        
        if(enemyRocket != null){
            if (enemyRocket.hasShield()){
                return;
            }
            else {
            getWorld().removeObject(enemyRocket);
            }
        }
        
        
    }
    
    private boolean hasShield(){
        return shieldActive;
    }
    
    private void resetSpecialEffect() {
        hasSpecialEffect = false;
        shieldActive = false;
        currentImageNoThrust = rocket;
        currentImageWithThrust = rocketWithThrust;
        laser = null;
    }
    
    private void spawnAsteroids() {
    // Add code here to spawn asteroids when case 4 is activated
    // You can customize the size, speed, and position of the asteroids
    // Example:
    for (int i = 0; i < 3; i++) {
        int size = Greenfoot.getRandomNumber(64) + 32; // Random size between 32 and 95
        Vector speed = new Vector(Greenfoot.getRandomNumber(360), 2);
        Asteroid asteroid = new Asteroid();
        getWorld().addObject(asteroid, Greenfoot.getRandomNumber(getWorld().getWidth()), Greenfoot.getRandomNumber(getWorld().getHeight()));
    }
}

private void createLaser(Actor rocket) {
    // Create a new Laser instance in front of the rocket
    double x = rocket.getX() + Math.cos(Math.toRadians(rocket.getRotation())) * rocket.getImage().getWidth() / 2;
    double y = rocket.getY() + Math.sin(Math.toRadians(rocket.getRotation())) * rocket.getImage().getHeight() / 2;
    laser = new Laser(rocket); // Pass the rocket reference to the Laser constructor
    getWorld().addObject(laser, (int) x, (int) y);
    laser.setRotation(rocket.getRotation());
}



}
    

