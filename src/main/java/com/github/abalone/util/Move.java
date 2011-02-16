package com.github.abalone.util;

import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Board;
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

   private Boolean isAIMove;

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
         this.finalBalls.add(nb);
      }

      this.isAIMove = false;
   }

   public Move(Direction direction) {
       this(null, direction);
   }

   public Boolean isAIMove() {
      return isAIMove;
   }

   public void setIsAIMove() {
      this.isAIMove = true;
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

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Move other = (Move) obj;
      if (this.initialBalls != other.initialBalls && (this.initialBalls == null || !this.initialBalls.equals(other.initialBalls))) {
         return false;
      }
      if (this.direction != other.direction) {
         return false;
      }
      return true;
   }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.initialBalls != null ? this.initialBalls.hashCode() : 0);
        hash = 43 * hash + (this.direction != null ? this.direction.hashCode() : 0);
        return hash;
    }
}
