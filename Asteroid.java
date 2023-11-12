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
    private int lifeTimer = 0;
    private static int LIFE_DURATION = 4 * 60;

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
        checkCollisionWithRocket();
        updateLifeTimer();
    }
    private void checkCollisionWithRocket() {
    if (isTouching(Rocket.class) || isTouching(Rocket2.class)) {
        Rocket rocket = (Rocket) getOneIntersectingObject(Rocket.class);
        Rocket2 rocket2 = (Rocket2) getOneIntersectingObject(Rocket2.class);

        if (rocket != null) {
            destroyRocket(rocket);
        } else if (rocket2 != null) {
            destroyRocket2(rocket2);
        }

        breakUp(); // Destroy the asteroid
    }
    }
    
    private void destroyRocket(Rocket rocket) {
    getWorld().addObject(new Explosion(), rocket.getX(), rocket.getY());
    getWorld().removeObject(rocket);
    }

    private void destroyRocket2(Rocket2 rocket2) {
        getWorld().addObject(new Explosion(), rocket2.getX(), rocket2.getY());
        getWorld().removeObject(rocket2);
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
    public void hit(int damage) {
    stability = stability - damage;
    if (stability <= 0) {
        destroyRocket();
        breakUp(); // Destroy the rocket
    }
    }   

    private void destroyRocket() {
        getWorld().addObject(new Explosion(), getX(), getY());
        getWorld().removeObject(this);
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
    
    public void updateLifeTimer(){
        lifeTimer++;
        if(lifeTimer >= LIFE_DURATION){
            getWorld().removeObject(this);
        }
    }
}
