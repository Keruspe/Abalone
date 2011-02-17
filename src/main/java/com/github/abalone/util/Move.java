package com.github.abalone.util;

import com.github.abalone.elements.Ball;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author keruspe
 */
public class Move implements Serializable {

   private Set<Ball> initialBalls;

   private Set<Ball> finalBalls;

   private Direction direction;


   public Move(Set<Ball> initialBalls, Direction direction) {
      this.initialBalls = new HashSet<Ball>();
      for (Ball b : initialBalls) {
         this.initialBalls.add(new Ball(b));
      }
      
      this.direction = direction;

      this.finalBalls = new HashSet<Ball>();
      for (Ball b : this.initialBalls) {
         Ball nb = new Ball(b);
         nb.move(this.direction);
         if ( nb.getColor() != Color.INVALID )
            this.finalBalls.add(nb);
      }
   }

   public Set<Ball> getFinalBalls() {
      return finalBalls;
   }

   public Set<Ball> getInitialBalls() {
      return initialBalls;
   }

   public Direction getDirection() {
      return direction;
   }
}
