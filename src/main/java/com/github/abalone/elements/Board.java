package com.github.abalone.elements;

import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

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
      if (col < 1 || row > 4 || row + col > 9) {
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
      for (int i = 1 ; i <= 5 ; ++i) {
         this.addBall(new Ball(Color.WHITE, -4, i));
         this.addBall(new Ball(Color.BLACK, 4, i));
      }
      for (int i = 1 ; i <= 6 ; ++i) {
         this.addBall(new Ball(Color.WHITE, -3, i));
         this.addBall(new Ball(Color.BLACK, 3, i));
      }
      for (int i = 3 ; i <= 5 ; ++i) {
         this.addBall(new Ball(Color.WHITE, -2, i));
         this.addBall(new Ball(Color.BLACK, 2, i));
      }
   }

   public Board getInstance() {
      if (Board.singleton == null) {
         Board.singleton = new Board();
      }
      return Board.singleton;
   }

    /**
     * @return the balls
     */
    public HashSet<Ball> getBalls() {
        return balls;
    }
    public void moveBallAtCoords(Coords c,String direction){
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
}
