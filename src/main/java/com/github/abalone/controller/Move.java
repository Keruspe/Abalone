package com.github.abalone.controller;

import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Board;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.util.Typelignepl;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author keruspe
 */
public class Move implements Serializable {

   private Set<Ball> initialBalls = null;
   private Set<Ball> finalBalls = null;
   private Direction direction;
   private Color color;
   
   private Boolean valid = false;


   public Move(Set<Ball> initialBalls, Direction direction, Color color) {
      this.initialBalls = new HashSet<Ball>();
      for (Ball b : initialBalls) {
         this.initialBalls.add(new Ball(b));
      }
      this.direction = direction;
      this.color = color;
   }

   public void compute(Board board) {
      Set<Ball> newInitials = validMove2(board);
      if ( newInitials != null ) {
         this.valid = true;
         this.initialBalls = newInitials;
         this.finalBalls = new HashSet<Ball>();
         for (Ball b : this.initialBalls) {
            Ball nb = new Ball(b);
            nb.move(this.direction);
            if ( nb.getColor() != Color.INVALID )
               this.finalBalls.add(nb);
         }
      }
   }


   //renvoi la bille la plus proche de la bille adverse ou de la case vide
   private Ball closest(Board board) {
      Coords closest = null;
      switch (this.direction) {
         case DOWNLEFT:
            closest = new Coords(-10, 10);
            for (Ball b : this.initialBalls) {
               if ((closest.getRow() < b.getCoords().getRow())
                       || (closest.getRow().equals(b.getCoords().getRow())
                       && closest.getCol() > b.getCoords().getCol())) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case DOWNRIGHT:
            closest = new Coords(-10, -10);
            for (Ball b : this.initialBalls) {
               if ((closest.getRow() < b.getCoords().getRow())
                       || (closest.getRow().equals(b.getCoords().getRow())
                       && closest.getCol() < b.getCoords().getCol())) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case UPLEFT:
            closest = new Coords(10, 10);
            for (Ball b : this.initialBalls) {
               if ((closest.getRow() > b.getCoords().getRow())
                       || (closest.getRow().equals(b.getCoords().getRow())
                       && closest.getCol() > b.getCoords().getCol())) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case UPRIGHT:
            closest = new Coords(10, -10);
            for (Ball b : this.initialBalls) {
               if ((closest.getRow() > b.getCoords().getRow())
                       || (closest.getRow().equals(b.getCoords().getRow())
                       && closest.getCol() < b.getCoords().getCol())) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case LEFT:
            closest = new Coords(10, 10);
            for (Ball b : this.initialBalls) {
               if (closest.getCol() > b.getCoords().getCol()) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case RIGHT:
            closest = new Coords(10, -10);
            for (Ball b : this.initialBalls) {
               if (closest.getCol() < b.getCoords().getCol()) {
                  closest = new Coords(b.getCoords());
               }
            }
      }
      return board.getBallAt(closest);

   }

   private Set<Ball> validMove2(Board board) {
      Iterator<Ball> itb = this.initialBalls.iterator();
      Set<Ball> result = new HashSet<Ball>();
      Ball b1;
      Ball b2;
      Ball b3;
      switch (this.initialBalls.size()) {
         case 1:
            b1 = itb.next();
            if (board.getBallAt(b1, this.direction).getColor() == Color.NONE) {
               result.add(b1);
            }
            break;
         case 2:
            b1 = itb.next();
            b2 = itb.next();
            if (Typelignepl.lesDirectionPerpendiculaire(b1.getCoords().LignePl(b2.getCoords())).contains(this.direction)) {
               Color nextColor1 = board.getBallAt(b1, this.direction).getColor();
               Color nextColor2 = board.getBallAt(b2, this.direction).getColor();
               if ((nextColor1 == Color.NONE) && (nextColor2 == Color.NONE)) {
                  result.add(b1);
                  result.add(b2);
               }
            } else {
               Ball closest = closest(board);
               Ball next = board.getBallAt(closest, this.direction);
               if (next.getColor() == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
               } else if (next.getColor() == this.color.other()) {
                  Color nextColor = board.getBallAt(next, this.direction).getColor();
                  if (nextColor == Color.NONE || nextColor == Color.INVALID) {
                     result.add(b1);
                     result.add(b2);
                     result.add(next);
                  }
               }
            }
            break;
         case 3:
            b1 = itb.next();
            b2 = itb.next();
            b3 = itb.next();
            Typelignepl linepl = b1.getCoords().LignePl(b2.getCoords());
            if (linepl == Typelignepl.NONADJACENT) {
               linepl = b1.getCoords().LignePl(b3.getCoords());
            }
            if (Typelignepl.lesDirectionPerpendiculaire(linepl).contains(this.direction)) {
               Color nextColor1 = board.getBallAt(b1, this.direction).getColor();
               Color nextColor2 = board.getBallAt(b2, this.direction).getColor();
               Color nextColor3 = board.getBallAt(b3, this.direction).getColor();
               if (nextColor1 == Color.NONE && nextColor2 == Color.NONE && nextColor3 == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
                  result.add(b3);
               }
            } else {
               Ball closest = closest(board);
               Ball next1 = board.getBallAt(closest, this.direction);
               if (next1.getColor() == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
                  result.add(b3);
               } else if (next1.getColor() == this.color.other()) {
                  Ball next2 = board.getBallAt(next1, this.direction);
                  Color nextColor2 = next2.getColor();
                  if (nextColor2 == Color.NONE || nextColor2 == Color.INVALID) {
                     result.add(b1);
                     result.add(b2);
                     result.add(b3);
                     result.add(next1);
                  } else {
                     Color nextColor3 = board.getBallAt(next2, this.direction).getColor();
                     if (nextColor3 == Color.NONE || nextColor3 == Color.INVALID) {
                        result.add(b1);
                        result.add(b2);
                        result.add(b3);
                        result.add(next1);
                        result.add(next2);
                     }
                  }
               }
            }
            break;
      }
      if ( result.isEmpty() )
         return null;
      else
         return result;
   }

   public Set<Ball> getFinalBalls() {
      return finalBalls;
   }

   public Set<Ball> getInitialBalls() {
      return initialBalls;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public Boolean isValid() {
      return this.valid;
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
        if (this.color != other.color) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.initialBalls != null ? this.initialBalls.hashCode() : 0);
        hash = 53 * hash + (this.direction != null ? this.direction.hashCode() : 0);
        hash = 53 * hash + (this.color != null ? this.color.hashCode() : 0);
        return hash;
    }
}
