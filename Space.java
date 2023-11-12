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
    private int player1Score = 0;
    private int player2Score = 0;
    public boolean respawnShield = true;
    private int itemSpawnTimer = 0;
    private static final int ITEM_SPAWN_INTERVAL = 7 * 60;  // 7 seconds at 60 frames per second
    private double gameTimer = 20.5 * 60; // 60 seconds * 60 frames per second
    private boolean gameOver = false;
    
    
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
        updateScore();
        spawnItemInCenter();
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
            }
        } else {
            checkitem();
            checkScore();
            // Game logic when the countdown is done
            // Other game logic...
            
             itemSpawnTimer++;
            if (itemSpawnTimer >= ITEM_SPAWN_INTERVAL) {
                // Reset the timer
                itemSpawnTimer = 0;
                // Spawn the item
                spawnItemInRandomPosition();
            }
            
            if (!gameOver) {
            updateGameTimer();
            // ... existing code ...
            } else {
                gameOver = true;
                if (gameOver){
                    Greenfoot.stop();
                }
            }   
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
    
    public void createRockets(){        
        Rocket player1Rocket = new Rocket();
        addObject(player1Rocket, 20, getHeight() / 2);
        
        Rocket2 player2Rocket = new Rocket2();
        addObject(player2Rocket, 800 - 20, getHeight() / 2);

        
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
    
    public void checkScore () {
        List<Rocket> player1List = getObjects(Rocket.class);
        List<Rocket2> player2List = getObjects(Rocket2.class);
        
        if (player1List.size() == 0) {
            player2Score++;
            respawnRocket1();
            updateScore();
        }
    
        if (player2List.size() == 0) {
            player1Score++;
            respawnRocket2();
            updateScore();
        }
    
    
    }

    public boolean respawnRocket1(){
        Rocket player1Rocket = new Rocket();
        addObject(player1Rocket, 20, getHeight() / 2);
        respawnShield = true;
        return respawnShield;
    }
    
    public boolean respawnRocket2(){
        Rocket2 player2Rocket = new Rocket2();
        addObject(player2Rocket, 800 - 20, getHeight() / 2);
        respawnShield = true;
        return respawnShield;
    }


    private void updateScore() {
        showText("Player 1 Score: " + player1Score, 100, 20);
        showText("Player 2 Score: " + player2Score, getWidth() - 100, 20);
    }
    
    public void checkitem () {
         List<Item> itemList = getObjects(Item.class);
    //überprüfen ob item aufgenommen wurde
    if (itemList.size() == 0 ) {
        spawnItemInRandomPosition();
    }
    }
    
    
    private void spawnItemInRandomPosition(){
        Item item = new Item();
        int x = Greenfoot.getRandomNumber(getWidth());
        int y = Greenfoot.getRandomNumber(getHeight());
        addObject(item, x, y);
    }
    
    private void updateGameTimer() {
        if (gameTimer > 0) {
            gameTimer--;
            showGameTimer();
        } else {
            // The game timer has reached 0, end the game
            gameOver = true;
            // Additional game over logic can be added here
        }
    }
    
    private void showGameTimer() {
        showText("Time: " + (gameTimer) + " seconds", getWidth() / 2, 20);
    }
}
