/**
 * Ein 2-D-Vektor.
 * 
 * @author Poul Henriksen
 * @author Michael Kölling
 * 
 * @version 2.0
 */
public class Vector
{
    double dx = 0;
    double dy = 0;
    int direction = 0;
    double length;
    
    /**
     * Erzeugt einen Standardvektor, initialisiert mit null.
     */
    public Vector()
    {
    }

    /**
     * Erzeugt einen Vektor gegebener Richtung und Länge.
     */
    public Vector(int direction, double length)
    {
       this.length = length;
       this.direction = direction;
       dx = length * Math.cos(Math.toRadians(direction));
       dy = length * Math.sin(Math.toRadians(direction));    
    }

    /**
     * Setzt die Richtung dieses Vektors.
     */
    public void setDirection(int direction) 
    {
        this.direction = direction;
        dx = length * Math.cos(Math.toRadians(direction));
        dy = length * Math.sin(Math.toRadians(direction));   
    }
   
    /**
     * Addiert einen weiteren Vektor zu diesem Vektor.
     */
    public void add(Vector other) 
    {
        dx += other.dx;
        dy += other.dy;    
        this.direction = (int) Math.toDegrees(Math.atan2(dy, dx));
        this.length = Math.sqrt(dx*dx+dy*dy);
    }   
    
    /**
     * Liefert den x-Wert dieses Vektors zurück.
     */
    public double getX() 
    {
        return dx;
    }
     
    /**
     * Liefert den y-Wert dieses Vektors zurück.
     */
    public double getY() 
    {
        return  dy;
    }
    
    /**
     * Liefert die aktuelle Richtung (in Grad) zurück.
     */
    public int getDirection() 
    {
        return direction;
    }
    
    /**
     * Liefert die aktuelle Länge des Vektors zurück.
     */
    public double getLength() 
    {
        return length;
    }
    
    /**
     * Erzeugt eine Kopie dieses Vektors.
     */
    public Vector copy() 
    {
        Vector copy = new Vector();
        copy.dx = dx;
        copy.dy = dy;
        copy.direction = direction;
        copy.length = length;
        return copy;
    }    
}
