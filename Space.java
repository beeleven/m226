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
}
