package com.github.abalone.elements;

import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.util.Move;
import com.github.abalone.util.Typelignepl;
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

   public Ball getBallAt(Ball ball, Direction direction) {
      Integer row = ball.getCoords().getRow();
      Integer col = ball.getCoords().getCol();
      switch (direction) {
         case UPLEFT:
            if (--row < 0) {
               --col;
            }
            break;
         case UPRIGHT:
            if (--row > -1) {
               ++col;
            }
            break;
         case LEFT:
            --col;
            break;
         case RIGHT:
            ++col;
            break;
         case DOWNLEFT:
            if (++row > 0) {
               --col;
            }
            break;
         case DOWNRIGHT:
            if (++row < 1) {
               ++col;
            }
            break;
      }
      Coords newCoords = new Coords(row, col);
      Ball returnBall = this.getBallAt(newCoords);
      if (returnBall == null) {
         returnBall = new Ball(this.elementAt(newCoords), newCoords);
      }
      return returnBall;
   }

   private Board() {
      this.filled = false;
   }

   public Board(Board other) {
      this.filled = true;
      this.balls = new HashSet<Ball>();
      for ( Ball b : other.balls )
          this.balls.add(new Ball(b));
   }

   public void fill(Game p) {
      if (this.filled) {
         return;
      }
      if (p == null) {
         for (int i = 0; i <= 4; ++i) {
            this.addBall(new Ball(Color.WHITE, -4, i));
            this.addBall(new Ball(Color.BLACK, 4, i));
         }
         for (int i = 0; i <= 5; ++i) {
            this.addBall(new Ball(Color.WHITE, -3, i));
            this.addBall(new Ball(Color.BLACK, 3, i));
         }
         for (int i = 2; i <= 4; ++i) {
            this.addBall(new Ball(Color.WHITE, -2, i));
            this.addBall(new Ball(Color.BLACK, 2, i));
         }
      } else {
         this.balls = p.getBoard().balls;
      }

      this.filled = true;
   }

   public static Board getInstance() {
      if (Board.singleton == null) {
         Board.singleton = new Board();
      }
      return Board.singleton;
   }

   /**
    * Returns the list of ball, read-only
    * @return the ball list as a {Set<Ball>}
    */
   public Set<Ball> getBalls() {
      return Collections.unmodifiableSet(this.balls);
   }

   public Integer ballsCount(Color color) {
      Integer count = 0;
      for (Ball b : balls) {
         if (b.getColor() == color) {
            ++count;
         }
      }
      return count;
   }

   public Color dominant() {
      Integer white = ballsCount(Color.WHITE);
      Integer black = ballsCount(Color.BLACK);
      if (white > black) {
         return Color.WHITE;
      } else if (black > white) {
         return Color.BLACK;
      } else {
         return Color.NONE;
      }
   }

   public Boolean loose(Color color) {
      return (ballsCount(color) < 9);
   }

   public void apply(Move move) {
      this.balls.removeAll(move.getInitialBalls());
      this.balls.addAll(move.getFinalBalls());
   }

   public void revert(Move move) {
      this.balls.removeAll(move.getFinalBalls());
      this.balls.addAll(move.getInitialBalls());
   }

   //renvoi la bille la plus proche de la bille adverse ou de la case vide
   private Ball closest(Set<Ball> selectedBalls, Direction to) {
      Coords closest = null;
      switch (to) {
         case DOWNLEFT:
            closest = new Coords(-10, 10);
            for (Ball b : selectedBalls) {
               if ((closest.getRow() < b.getCoords().getRow())
                       || (closest.getRow().equals(b.getCoords().getRow())
                       && closest.getCol() > b.getCoords().getCol())) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case DOWNRIGHT:
            closest = new Coords(-10, -10);
            for (Ball b : selectedBalls) {
               if ((closest.getRow() < b.getCoords().getRow())
                       || (closest.getRow().equals(b.getCoords().getRow())
                       && closest.getCol() < b.getCoords().getCol())) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case UPLEFT:
            closest = new Coords(10, 10);
            for (Ball b : selectedBalls) {
               if ((closest.getRow() > b.getCoords().getRow())
                       || (closest.getRow().equals(b.getCoords().getRow())
                       && closest.getCol() > b.getCoords().getCol())) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case UPRIGHT:
            closest = new Coords(10, -10);
            for (Ball b : selectedBalls) {
               if ((closest.getRow() > b.getCoords().getRow())
                       || (closest.getRow().equals(b.getCoords().getRow())
                       && closest.getCol() < b.getCoords().getCol())) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case LEFT:
            closest = new Coords(10, 10);
            for (Ball b : selectedBalls) {
               if (closest.getCol() > b.getCoords().getCol()) {
                  closest = new Coords(b.getCoords());
               }
            }
            break;
         case RIGHT:
            closest = new Coords(10, -10);
            for (Ball b : selectedBalls) {
               if (closest.getCol() < b.getCoords().getCol()) {
                  closest = new Coords(b.getCoords());
               }
            }
      }
      return this.getBallAt(closest);

   }

   private Move getValidMove(Set<Ball> selectedBalls, Direction direction, Color selfColor) {
      Iterator<Ball> itb = selectedBalls.iterator();
      Set<Ball> result = new HashSet<Ball>();
      Ball b1, b2, b3;
      switch (selectedBalls.size()) {
         case 1:
            b1 = itb.next();
            if (this.getBallAt(b1, direction).getColor() == Color.NONE) {
               result.add(b1);
            }
            break;
         case 2:
            b1 = itb.next();
            b2 = itb.next();
            if (Typelignepl.lesDirectionPerpendiculaire(b1.getCoords().LignePl(b2.getCoords())).contains(direction)) {
               Color nextColor1 = this.getBallAt(b1, direction).getColor();
               Color nextColor2 = this.getBallAt(b2, direction).getColor();
               if ((nextColor1 == Color.NONE) && (nextColor2 == Color.NONE)) {
                  result.add(b1);
                  result.add(b2);
               }
            } else {
               Ball closest = closest(selectedBalls, direction);
               if ( closest == null )
                   break;
               Ball next = this.getBallAt(closest, direction);
               if (next.getColor() == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
               } else if (next.getColor() == selfColor.other()) {
                  Color nextColor = this.getBallAt(next, direction).getColor();
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
            if (Typelignepl.lesDirectionPerpendiculaire(linepl).contains(direction)) {
               Color nextColor1 = this.getBallAt(b1, direction).getColor();
               Color nextColor2 = this.getBallAt(b2, direction).getColor();
               Color nextColor3 = this.getBallAt(b3, direction).getColor();
               if (nextColor1 == Color.NONE && nextColor2 == Color.NONE && nextColor3 == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
                  result.add(b3);
               }
            } else {
               Ball closest = closest(selectedBalls, direction);
               Ball next1 = this.getBallAt(closest, direction);
               if (next1.getColor() == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
                  result.add(b3);
               } else if (next1.getColor() == selfColor.other()) {
                  Ball next2 = this.getBallAt(next1, direction);
                  Color nextColor2 = next2.getColor();
                  if (nextColor2 == Color.NONE || nextColor2 == Color.INVALID) {
                     result.add(b1);
                     result.add(b2);
                     result.add(b3);
                     result.add(next1);
                  } else {
                     Color nextColor3 = this.getBallAt(next2, direction).getColor();
                     if (!nextColor3.isPlayer()) {
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
         return new Move(result, direction);
   }

   private Boolean ballsAreALine(Set<Ball> balls) {
      Set<Coords> coords = new HashSet<Coords>();
      for ( Ball b : balls )
         coords.add(b.getCoords());
      return areALine(coords);
   }

   private Boolean areALine(Set<Coords> coords) {
      Iterator<Coords> itc = coords.iterator();
      Coords c1, c2, c3;
      switch (coords.size()) {
         case 1:
            return Boolean.TRUE;
         case 2:
            c1 = itc.next();
            c2 = itc.next();
            if (c1.getRow().equals(c2.getRow())) {
               return (Math.abs(c1.getCol() - c2.getCol()) == 1);
            } else if (Math.abs(c1.getRow() - c2.getRow()) != 1) {
               return Boolean.FALSE;
            } else {
               Integer diff;
               if (c1.getRow() < c2.getRow()) {
                  diff = c2.getCol() - c1.getCol();
               } else {
                  diff = c1.getCol() - c2.getCol();
               }
               if (c1.getRow() < 0) {
                  return (diff == 0 || diff == 1);
               } else {
                  return (diff == 0 || diff == -1);
               }
            }
         case 3:
            c1 = itc.next();
            c2 = itc.next();
            c3 = itc.next();
            Set<Coords> sub1 = new HashSet<Coords>();
            sub1.add(c1);
            sub1.add(c2);
            Set<Coords> sub2 = new HashSet<Coords>();
            sub2.add(c1);
            sub2.add(c3);
            Integer colModifier = 0;
            if (areALine(sub1)) {
               if (!c3.getRow().equals(0)) {
                  if ((c1.getRow().equals(0)
                          && !c2.getRow().equals(0)
                          && (c2.getRow() == -c3.getRow()))
                          || (c2.getRow().equals(0)
                          && (c1.getRow() == -c3.getRow()))) {
                     colModifier = -1;
                  }
               }
               return ((c3.getRow().equals(2 * c2.getRow() - c1.getRow()) && c3.getCol().equals(2 * c2.getCol() - c1.getCol() + colModifier))
                       || (c3.getRow().equals(2 * c1.getRow() - c2.getRow()) && c3.getCol().equals(2 * c1.getCol() - c2.getCol() + colModifier)));
            } else if (areALine(sub2)) {
               if (!c2.getRow().equals(0)) {
                  if ((c1.getRow().equals(0)
                          && !c3.getRow().equals(0)
                          && (c2.getRow() == -c3.getRow()))
                          || (c3.getRow().equals(0)
                          && (c2.getRow() == -c1.getRow()))) {
                     colModifier = -1;
                  }
               }
               return ((c2.getRow().equals(2 * c3.getRow() - c1.getRow()) && c2.getCol().equals(2 * c3.getCol() - c1.getCol() + colModifier))
                       || (c2.getRow().equals(2 * c1.getRow() - c3.getRow()) && c2.getCol().equals(2 * c1.getCol() - c3.getCol() + colModifier)));
            }
      }
      return Boolean.FALSE;
   }


   public Move getValidMoveCoords(Set<Coords> selectedBallsCoords, Direction direction, Color current) {
      if (!areALine(selectedBallsCoords)) {
         return null;
      }
      Set<Ball> selectedBalls = this.getBallsAt(selectedBallsCoords, current);
      if (selectedBalls.isEmpty())
         return null;
      return getValidMove(selectedBalls, direction, current);
   }

   public Set<Move> validMoves(Set<Ball> selectedBalls, Color color) {
      Set<Move> answer = new HashSet<Move>();
      for ( Direction d : Direction.values() ) {
         Move m = getValidMove(selectedBalls, d, color);
         if ( m != null ) {
            answer.add(m);
         }
      }
      return answer;
   }

   public Set<Direction> validDirections(Set<Ball> selectedBalls, Color color) {
      Set<Direction> answer = new HashSet<Direction>();
      for ( Direction d : Direction.values() ) {
         if ( getValidMove(selectedBalls, d, color) != null ) {
            answer.add(d);
         }
      }
      return answer;
   }

   public Set<Direction> validDirectionsCoords(Set<Coords> selectedBallsCoords, Color color) {
      Set<Ball> selectedBalls = this.getBallsAt(selectedBallsCoords, color);
      return this.validDirections(selectedBalls, color);
   }

    private Set<Ball> getBallsAt(Set<Coords> selectedBallsCoords, Color color) {
      Set<Ball> selectedBalls = new HashSet<Ball>();
      for (Coords c : selectedBallsCoords) {
         Ball b = this.getBallAt(c);
         System.out.println(b);
         if (b.getColor() != color) {
            return new HashSet<Ball>();
         }
         selectedBalls.add(b);
      }
      return selectedBalls;
    }


   public HashSet<Move> getPossibleMoves(Color player) {
      HashSet<Move> moves = new HashSet<Move>();
      HashSet<Set<Ball>> sets = new HashSet<Set<Ball>>();

      Set<Ball> balls1 = new HashSet<Ball>();
      for (Ball b : this.balls) {
         if (b.getColor().equals(player))
            balls1.add(b);
      }
      Set<Ball> balls2 = new HashSet<Ball>(balls1);
      for (Ball b1 : balls1) {
         Set<Ball> ballsToMove = new HashSet<Ball>();
         ballsToMove.add(b1);
         sets.add(ballsToMove);

         balls2.remove(b1);
         Set<Ball> balls3 = new HashSet<Ball>(balls2);
         for (Ball b2 : balls2) {
            Set<Ball> ballsToMove2 = new HashSet<Ball>(ballsToMove);
            ballsToMove2.add(b2);
            if ( this.ballsAreALine(ballsToMove2)) {
               sets.add(ballsToMove2);

               balls3.remove(b2);
               for (Ball b3 : balls3) {
                  Set<Ball> ballsToMove3 = new HashSet<Ball>(ballsToMove2);
                  ballsToMove3.add(b3);
                  if ( this.ballsAreALine(ballsToMove3))
                     sets.add(ballsToMove3);
               }
	    }
         }
      }
      for ( Set<Ball> ballsToMove : sets ) {
         moves.addAll(this.validMoves(ballsToMove, player));
      }
      return moves;
   }
}
