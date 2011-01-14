package com.github.abalone.elements;

import com.github.abalone.util.Color;
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
