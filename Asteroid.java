import greenfoot.*;  // (World, Actor, GreenfootImage und Greenfoot)

/**
 * Ein Gesteinsbrocken im Weltraum.
 *  
 * @author Poul Henriksen
 * @author Michael Kölling
 * 
 * @version 2.0
 */
public class Asteroid extends Mover
{
    /** Größe dieses Asteroiden */
    private int size;

    /** Wenn die Stabilität 0 erreicht ist, explodiert der Asteroid */
    private int stability;

    /**
     * Erzeugt einen Asteroiden mit einer Standardgröße und -geschwindigkeit.
     */
    public Asteroid()
    {
        this(64);
    }
    
    /**
     * Erzeugt einen Asteroiden mit einer gegebenen Größe, zufälligen Bewegungsrichtung und Standardgeschwindigkeit.
     */
    public Asteroid(int size)
    {
        this(size, new Vector(Greenfoot.getRandomNumber(360), 2));
    }
    
    /**
     * Erzeugt einen Asteroiden mit einer gegebenen Größe, Richtung und Geschwindigkeit.
     */
    private Asteroid(int size, Vector speed)
    {
        super(speed);
        setSize(size);
    }
    
    /**
     * Lässt den Asteroiden agieren; d.h. herumfliegen.
     */
    public void act()
    {         
        move();
    }

    /**
     * Setzt die Größe dieses Asteroiden. Beachte, dass die Stabilität im direkten
     * Zusammenhang mit der Größe steht. Kleinere Asteroide sind weniger stabil.
     */
    public void setSize(int size) 
    {
        this.size = size;
        stability = size;
        GreenfootImage image = getImage();
        image.scale(size, size);
    }

    /**
     * Liefert die aktuelle Stabilität dieses Asteroiden. (Wenn dieser Wert null wird,
     * zerfällt der Asteroid.)
     */
    public int getStability() 
    {
        return stability;
    }
    
    /**
     * Trifft diesen Asteroiden und richtet den angegebenen Schaden an.
     */
    public void hit(int damage) 
    {
        stability = stability - damage;
        if (stability <= 0) {
            breakUp();       
        }
    }
    
    /**
     * Zerbricht diesen Asteroiden in zwei kleinere Asteroide (oder falls er schon  
     * sehr klein ist, verschwindet er einfach).
     */
    private void breakUp() 
    {
        Greenfoot.playSound("Explosion.wav");
        
        if (size <= 16) {
            // wenn schon sehr klein, einfach verschwinden 
            getWorld().removeObject(this);
            return;
        }
        else {
            // ansonsten erzeuge zwei Asteroiden mit der halben Größe
            int r = getMovement().getDirection() + Greenfoot.getRandomNumber(45);
            double l = getMovement().getLength();
            Vector speed1 = new Vector(r + 60, l * 1.2);
            Vector speed2 = new Vector(r - 60, l * 1.2);        
            Asteroid a1 = new Asteroid(size/2, speed1);
            Asteroid a2 = new Asteroid(size/2, speed2);
            getWorld().addObject(a1, getX(), getY());
            getWorld().addObject(a2, getX(), getY());        
            a1.move();
            a2.move();
        
            getWorld().removeObject(this);
        }
    }
}
