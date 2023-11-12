import greenfoot.*;  // (World, Actor, GreenfootImage und Greenfoot)
import java.util.*;


/**
 * Weltraum. Etwas, in dem Raketen fliegen ...
 * 
 * @author Michael Kölling
 * @version 2.0
 */
public class Space extends World
{
    private int SpawnTimer = 0;
    private static final int SPAWN_INTERVAL = 15 * 60;
    private Random random = new Random();
    // Add a variable for countdown time
    private int countdown = 300;  // 300 frames, assuming 60 frames per second
    private boolean countdownActive = true;  // Indicates whether the countdown is active
    
    
    /**
     * Erzeugt die Weltraum-Welt mit schwarzem Hintergrund und Sternen.
     */
    public Space() 
    {
        super(800, 600, 1);
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();
        createStars(300);
        Explosion.initialiseImages();
        createRockets();
    }
    
    public void act(){
        // Check if the countdown is greater than zero
        if (countdownActive) {
            showCountdown();
            countdown -= 2;
            if (countdown <= 0) {
                countdownActive = false;  // Countdown is done, allow user interaction
                // Additional setup or game logic after countdown
                showText("", getWidth() / 2, getHeight() / 2);
                spawnItemInCenter();
            }
        } else {
            // Game logic when the countdown is done
            // Other game logic...
        }
    }
    
    
    
    /**
     * Erzeugt einige zufällige Sterne in der Welt.
     */
    private void createStars(int number) 
    {
        GreenfootImage background = getBackground();             
        for (int i=0; i < number; i++) {            
             int x = Greenfoot.getRandomNumber( getWidth() );
             int y = Greenfoot.getRandomNumber( getHeight() );
             int color = 150 - Greenfoot.getRandomNumber(120);
             background.setColorAt(x, y, new Color(color,color,color));
        }
    }
    
    private void createRockets(){        
        Rocket player1Rocket = new Rocket();
        addObject(player1Rocket, 20, getHeight() / 2);
        
        Rocket2 player2Rocket = new Rocket2();
        addObject(player2Rocket, 800 - 20, getHeight() / 2);
        
    }
    
    private void spawnItem(){
        
    }
    
    private void showCountdown() {
        showText("Countdown: " + countdown / 60, getWidth() / 2, getHeight() / 2);
    }
    
    public boolean isCountdownActive() {
        return countdownActive;
    }
    
    private void spawnItemInCenter() {
        int yOffset = 100;
        Item item = new Item();
        addObject(item, getWidth() / 2, yOffset);
    }
}
