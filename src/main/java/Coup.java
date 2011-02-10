
import com.github.abalone.elements.Ball;
import com.github.abalone.util.Coords;
import java.util.Set;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joe
 */
public class Coup {
   private Set <Ball> balls;
   private  String direction;
   private Coords from;
   private Coords to;
    /**
     * @return the balls
     */
    public Set<Ball> getBalls() {
        return balls;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @return the from
     */
    public Coords getFrom() {
        return from;
    }

    /**
     * @return the to
     */
    public Coords getTo() {
        return to;
    }
    

}
