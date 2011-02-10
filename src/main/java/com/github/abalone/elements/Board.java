package com.github.abalone.elements;

import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author keruspe
 */
public class Board implements Serializable {
   private static Board singleton;
   private HashSet<Ball> balls = new HashSet<Ball>(28);

   private void addBall(Ball ball) {
        this.getBalls().add(ball);
   }

   public Color elementAt(Coords coords) {
      Integer col = coords.getCol();
      Integer row = Math.abs(coords.getRow());
      if (col < 0 || row > 4 || row + col > 8) {
         return Color.INVALID;
      }
      Ball ball = new Ball(Color.WHITE, coords);
      if (this.getBalls().contains(ball)) {
         return Color.WHITE;
      }
      ball.setColor(Color.BLACK);
      if (this.getBalls().contains(ball)) {
         return Color.BLACK;
      }
      return Color.NONE;
   }
   private Board() {
      for (int i = 0 ; i <= 4 ; ++i) {
         this.addBall(new Ball(Color.WHITE, -4, i));
         this.addBall(new Ball(Color.BLACK, 4, i));
      }
      for (int i = 0 ; i <= 5 ; ++i) {
         this.addBall(new Ball(Color.WHITE, -3, i));
         this.addBall(new Ball(Color.BLACK, 3, i));
      }
      for (int i = 2 ; i <= 4 ; ++i) {
         this.addBall(new Ball(Color.WHITE, -2, i));
         this.addBall(new Ball(Color.BLACK, 2, i));
      }
   }

   public static Board getInstance() {
      if (Board.singleton == null) {
         Board.singleton = new Board();
      }
      return Board.singleton;
   }

    public void moveBallAtCoords(Coords c,Direction direction){
        Iterator itb=balls.iterator();
        boolean trouverb=false;
        Ball b;
        while(itb.hasNext()&& !trouverb){
            b=(Ball)itb.next();
            if(b.getCoords().equals(c)){
                b.move(direction);
                trouverb=true;
            }
        }
    }

   /**
    * Returns the list of ball, read-only
    * @return the ball list as a {Set<Ball>}
    */
   public Set<Ball> getBalls()
   {
       return Collections.unmodifiableSet(this.balls);
   }
}
