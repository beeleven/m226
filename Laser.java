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



    public Laser() {
        GreenfootImage image = new GreenfootImage(LENGTH, 1);
        image.setColor(Color.BLUE); // Set the color of the laser
        image.drawLine(0, 0, LENGTH - 1, 0);
        setImage(image);
    }
    
    
    /**
     * Act - do whatever the Laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        positionRelativeToRocket();
        move(5); // Adjust the speed as needed
        checkCollision();
        updateLifeTimer();
    }

    private void checkCollision() {
        Rocket2 rocket2 = (Rocket2) getOneIntersectingObject(Rocket2.class);
        if (rocket2 != null) {
            //getWorld().addObject(new Explosion(), getX(), getY());
            getWorld().removeObject(rocket2);
            getWorld().removeObject(this);
        }
    }
    
    private void positionRelativeToRocket() {
        Rocket rocket = (Rocket) getWorld().getObjects(Rocket.class).get(0);
        double angle = Math.toRadians(rocket.getRotation()); // Convert the angle to radians
        int offsetX = (int) (Math.cos(angle) * DISTANCE_FROM_ROCKET);
        int offsetY = (int) (Math.sin(angle) * DISTANCE_FROM_ROCKET);
        setLocation(rocket.getX() + offsetX, rocket.getY() + offsetY);
        setRotation(rocket.getRotation());
    }
    
     private void updateLifeTimer() {
        lifeTimer++;
        if (lifeTimer >= LIFE_DURATION) {
            // Life duration expired, remove the laser
            getWorld().removeObject(this);
        }
    }
}
