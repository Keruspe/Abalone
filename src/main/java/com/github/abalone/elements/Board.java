package com.github.abalone.elements;

import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @author keruspe
 */
public class Board implements Serializable {
   private static Board singleton;
   private HashSet<Ball> balls = new HashSet<Ball>(28);

   private void addBall(Ball ball) {
      this.balls.add(ball);
   }

   public Color elementAt(Coords coords) {
      Integer col = coords.getCol();
      if (col < 1) {
         return Color.INVALID;
      }
      switch (coords.getRow()) {
         case 1:
         case 9:
            if (col > 5) {
               return Color.INVALID;
            }
            break;
         case 2:
         case 8:
            if (col > 6) {
               return Color.INVALID;
            }
            break;
         case 3:
         case 7:
            if (col > 7) {
               return Color.INVALID;
            }
            break;
         case 4:
         case 6:
            if (col > 8) {
               return Color.INVALID;
            }
            break;
         case 5:
            if (col > 9) {
               return Color.INVALID;
            }
            break;
         default:
            return Color.INVALID;
      }
      Ball ball = new Ball(Color.WHITE, coords);
      if (this.balls.contains(ball)) {
         return Color.WHITE;
      }
      ball.setColor(Color.BLACK);
      if (this.balls.contains(ball)) {
         return Color.BLACK;
      }
      return Color.NONE;
   }

   private Board() {
      for (int i = 1 ; i <= 5 ; ++i) {
         this.addBall(new Ball(Color.WHITE, 1, i));
         this.addBall(new Ball(Color.BLACK, 9, i));
      }
      for (int i = 1 ; i <= 6 ; ++i) {
         this.addBall(new Ball(Color.WHITE, 2, i));
         this.addBall(new Ball(Color.BLACK, 8, i));
      }
      for (int i = 3 ; i <= 5 ; ++i) {
         this.addBall(new Ball(Color.WHITE, 3, i));
         this.addBall(new Ball(Color.BLACK, 7, i));
      }
   }

   public Board getInstance() {
      if (Board.singleton == null) {
         Board.singleton = new Board();
      }
      return Board.singleton;
   }
}
