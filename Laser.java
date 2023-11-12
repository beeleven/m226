import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laser extends Mover
{
    private static final int LENGTH = 80; // Adjust the length of the laser as needed
    private static final int DISTANCE_FROM_ROCKET = 60; // Adjust the distance from the rocket
    private int lifeTimer = 0;
    private static final int LIFE_DURATION = 2 * 60;  // 7 seconds at 60 frames per second



    public Laser(Actor rocket) {
        this.associatedRocket = rocket;
        GreenfootImage image = new GreenfootImage(LENGTH, 1);
        image.setColor(Color.BLUE); // Set the color of the laser
        image.drawLine(0, 0, LENGTH - 1, 0);
        setImage(image);
    }
    
    private Actor associatedRocket;
    /**
     * Act - do whatever the Laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        World world = getWorld();
        
        
        
        positionRelativeToRocket();
        move(5); // Adjust the speed as needed
        checkCollision();
        updateLifeTimer();
    }

    private void checkCollision() {
        if (associatedRocket instanceof Rocket) {
            Rocket rocket = (Rocket) associatedRocket;
            Rocket2 rocket2 = (Rocket2) getOneIntersectingObject(Rocket2.class);
            if (rocket2 != null) {
                getWorld().removeObject(rocket2);
                getWorld().removeObject(this);
            }
        } else if (associatedRocket instanceof Rocket2) {
            Rocket2 rocket2 = (Rocket2) associatedRocket;
            Rocket rocket = (Rocket) getOneIntersectingObject(Rocket.class);
            if (rocket != null) {
                getWorld().removeObject(rocket);
                getWorld().removeObject(this);
            }
        }
    }
    
    private void positionRelativeToRocket() {
        double angle = Math.toRadians(associatedRocket.getRotation()); // Convert the angle to radians
        int offsetX = (int) (Math.cos(angle) * DISTANCE_FROM_ROCKET);
        int offsetY = (int) (Math.sin(angle) * DISTANCE_FROM_ROCKET);
        setLocation(associatedRocket.getX() + offsetX, associatedRocket.getY() + offsetY);
        setRotation(associatedRocket.getRotation());
    }
    
     private void updateLifeTimer() {
        lifeTimer++;
        if (lifeTimer >= LIFE_DURATION) {
            // Life duration expired, remove the laser
            getWorld().removeObject(this);
        }
    }
}
