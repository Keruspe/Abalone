package com.github.abalone.elements;

import com.github.abalone.util.Move;
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
    private boolean filled;

    private void addBall(Ball ball) {
        this.balls.add(ball);
    }

    public Color elementAt(Coords coords) {
        Integer col = coords.getCol();
        Integer row = Math.abs(coords.getRow());
        if (col < 0 || row > 4 || row + col > 8) {
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

    public Ball getBallAt(Coords coords) {
        Integer col = coords.getCol();
        Integer row = Math.abs(coords.getRow());
        if (col < 0 || row > 4 || row + col > 8) {
            return null;
        }
        Ball ball = new Ball(Color.WHITE, coords);
        if (this.balls.contains(ball)) {
            return ball;
        }
        ball.setColor(Color.BLACK);
        if (this.balls.contains(ball)) {
            return ball;
        }
        return null;
    }

    public Ball getBallAt(Ball ball, Direction direction){
        Ball returnBall = new Ball(ball.getColor(), null);

        Integer row = ball.getCoords().getRow();
        Integer col = ball.getCoords().getCol();
        switch ( direction )
        {
        case UPLEFT:
            if (row++ < 1)
                --col;
        break;
        case UPRIGHT:
            if (row++ > 0)
               ++col;
        break;
        case LEFT:
            --col;
        break;
        case RIGHT:
            ++col;
        break;
        case DOWNLEFT:
            if (row-- < 1)
                --col;
        break;
        case DOWNRIGHT:
            if (row++ > 0)
                ++col;
        break;
        }
        returnBall.SetCoords(row, col);
        return returnBall;
    }
    
    private Board() {
        this.filled = false;
    }

    public void fill(Game p)
    {
        if ( this.filled )
            return;
        if ( p == null )
        {
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
        } else
            this.balls = (HashSet<Ball>) p.getBoard().getBalls();

        this.filled = true;
    }

    public static Board getInstance() {
        if (Board.singleton == null) {
            Board.singleton = new Board();
        }
        return Board.singleton;
    }

    public Move generatorOfMove(Set<Coords> selectedBalls, Direction direction){
            Move mouvement= new Move();
            Iterator itc=selectedBalls.iterator();
            Coords c;

            while(itc.hasNext()){
                c=(Coords) itc.next();
               Ball bc=new Ball(this.elementAt(c), c);
               Ball bv= new Ball(this.elementAt(c),c.moveTo(direction));
               mouvement.unMouvement.put(bc,bv );

            }
            return mouvement;
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

    /// Do a move
    /**
     * Check if the wanted move is correct and what
     * effect will it have
     *
     * @param coords The list of coordinates of the balls to move
     * @param direction The direction in which we want to move the balls
     */
    public void move(Set<Ball> selectedBalls, Direction direction) {
        for (Ball b : selectedBalls) {
           b.move(direction);
        }

        /*
         * TODO: the addMove have to be in an History class
         */
        //p.addMove(generatorOfMove(selectedBalls, direction));
    }
}
