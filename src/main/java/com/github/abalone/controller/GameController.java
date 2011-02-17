package com.github.abalone.controller;

import com.github.abalone.ai.AI;
import com.github.abalone.config.Config;
import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Board;
import com.github.abalone.elements.Game;
import com.github.abalone.util.Typelignepl;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.util.GameState;
import com.github.abalone.util.Move;
import com.github.abalone.view.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author keruspe
 * @author sardemff7
 */
public class GameController {

   private static GameController singleton;

   private Window window;

   private Game game;

   private Move currentBestMove;

   private GameController() {
   }

   public static GameController getInstance() {
      if (GameController.singleton == null) {
         GameController.singleton = new GameController();
      }
      return GameController.singleton;
   }

   /// Launch a new game
   public void launch() {
      Board.getInstance().fill(null);
      this.game = new Game(Color.WHITE, -1, -1);
      AI.init(this.game, ((Boolean) Config.get("AI")) ? Color.BLACK : Color.NONE);
      this.currentBestMove = AI.getInstance().getBestMove(this.game.getTurn());
      this.window.updateBoard(this.game.getTurn());
   }

   /// Save the game
   public void save() {
      FileOutputStream fos = null;
      ObjectOutputStream oos = null;
      try {
         File f = new File("abalone.save");
         fos = new FileOutputStream(f);
         oos = new ObjectOutputStream(fos);
         oos.writeObject(this.game);
      } catch (Exception ex) {
         Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
         try {
            fos.close();
            oos.close();
         } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }

   /// Load the saved game
   public Boolean load() {
      FileInputStream fis = null;
      ObjectInputStream ois = null;
      try {
         File f = new File("abalone.save");
         fis = new FileInputStream(f);
         ois = new ObjectInputStream(fis);
         Game loadedGame = (Game) ois.readObject();
         this.game = new Game(loadedGame.getTurn(), loadedGame.getTimeLeft(), loadedGame.getTurnsLeft());
         this.game.setHistory(loadedGame.getHistory());
         this.game.setBoard(Board.getInstance());
         this.game.getBoard().fill(loadedGame);
         AI.init(this.game, ((Boolean) Config.get("AI")) ? Color.BLACK : Color.NONE);
         this.currentBestMove = AI.getInstance().getBestMove(this.game.getTurn());
         this.window.updateBoard(this.game.getTurn());
      } catch (Exception ex) {
         Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
         return Boolean.FALSE;
      } finally {
         try {
            if ( fis != null )
               fis.close();
            if ( ois != null )
               ois.close();
         } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
      this.window.updateBoard(this.game.getTurn());
      return Boolean.TRUE;
   }

   /// Quit the game
   public void quit() {
      System.exit(0);
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
      return this.game.getBoard().getBallAt(closest);

   }

   private Set<Ball> validMove2(Set<Ball> selectedBalls, Direction direction, Color selfColor) {
      Iterator<Ball> itb = selectedBalls.iterator();
      Set<Ball> result = new HashSet<Ball>();
      Ball b1;
      Ball b2;
      Ball b3;
      switch (selectedBalls.size()) {
         case 1:
            b1 = itb.next();
            if (this.game.getBoard().getBallAt(b1, direction).getColor() == Color.NONE) {
               result.add(b1);
            }
            break;
         case 2:
            b1 = itb.next();
            b2 = itb.next();
            if (Typelignepl.lesDirectionPerpendiculaire(b1.getCoords().LignePl(b2.getCoords())).contains(direction)) {
               Color nextColor1 = this.game.getBoard().getBallAt(b1, direction).getColor();
               Color nextColor2 = this.game.getBoard().getBallAt(b2, direction).getColor();
               if ((nextColor1 == Color.NONE) && (nextColor2 == Color.NONE)) {
                  result.add(b1);
                  result.add(b2);
               }
            } else {
               Ball closest = closest(selectedBalls, direction);
               Ball next = this.game.getBoard().getBallAt(closest, direction);
               if (next.getColor() == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
               } else if (next.getColor() == selfColor.other()) {
                  Color nextColor = this.game.getBoard().getBallAt(next, direction).getColor();
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
               Color nextColor1 = this.game.getBoard().getBallAt(b1, direction).getColor();
               Color nextColor2 = this.game.getBoard().getBallAt(b2, direction).getColor();
               Color nextColor3 = this.game.getBoard().getBallAt(b3, direction).getColor();
               if (nextColor1 == Color.NONE && nextColor2 == Color.NONE && nextColor3 == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
                  result.add(b3);
               }
            } else {
               Ball closest = closest(selectedBalls, direction);
               Ball next1 = this.game.getBoard().getBallAt(closest, direction);
               if (next1.getColor() == Color.NONE) {
                  result.add(b1);
                  result.add(b2);
                  result.add(b3);
               } else if (next1.getColor() == selfColor.other()) {
                  Ball next2 = this.game.getBoard().getBallAt(next1, direction);
                  Color nextColor2 = next2.getColor();
                  if (nextColor2 == Color.NONE || nextColor2 == Color.INVALID) {
                     result.add(b1);
                     result.add(b2);
                     result.add(b3);
                     result.add(next1);
                  } else {
                     Color nextColor3 = this.game.getBoard().getBallAt(next2, direction).getColor();
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

   public Set<Ball> validMove(Set<Coords> selectedBallsCoords, Direction direction, Color current) {
      Set<Ball> selectedBalls = new HashSet<Ball>();
      for (Coords c : selectedBallsCoords) {
         Ball b = this.game.getBoard().getBallAt(c);
         if (b.getColor() != current) {
                return null;
         }
         selectedBalls.add(b);
      }
      if (!this.game.getBoard().areALine(selectedBalls)) {
         return null;
      }
      return validMove2(selectedBalls, direction, current);
   }

   private Boolean validMove(Set<Coords> selectedBallsCoords, Direction direction) {
      Set<Ball> balls = validMove(selectedBallsCoords, direction, this.game.getTurn());
      return (balls != null);
   }

   public Set<Direction> validDirections(Set<Coords> selectedBallsCoords) {
      Set<Direction> answer = new HashSet<Direction>();
      for ( Direction d : Direction.values() ) {
         if (validMove(selectedBallsCoords, d)) {
            answer.add(d);
         }
      }
      return answer;
   }

   public GameState doMove(Move move, Boolean AIPlaying) {
      Color current = this.game.getTurn();
      Boolean AITurn = false;
      if (current == Color.NONE) {
         return GameState.OUTOFTURNS;
      } else if (this.game.over()) {
         return GameState.WON;
      } else if (current.equals(AI.getInstance().getColor())) {
         AITurn = true;
         if ( !AIPlaying )
           return GameState.RUNNING;
      }
      this.game.getBoard().apply(move);
      this.game.addToHistory(move);
      Move bestMove = AI.getInstance().getBestMove(this.game.getNextTurn());
      if (AITurn) {
         this.currentBestMove = bestMove;
      }
      this.window.updateBoard(this.game.getTurn());
      return GameState.RUNNING;
   }

   public GameState move(Set<Coords> selectedBallsCoords, Direction direction) {
      Move move = this.game.getBoard().getMove(selectedBallsCoords, direction, this.game.getTurn());
      return doMove(move, Boolean.FALSE);
   }

   public Move getCurrentBestMove() {
      return currentBestMove;
   }

   private void doGoBack() {
      int lastIndex = this.game.getHistory().size() - 1;
      if (lastIndex != -1) {
         this.game.getPreviousTurn();
         Move move = this.game.getHistory().get(lastIndex);
         this.game.getHistory().remove(move);
         this.game.getBoard().revert(move);
         this.window.updateBoard(this.game.getTurn());
      }
   }

   public void goBack() {
      if (this.game == null) {
         return;
      }
      doGoBack();
      if (AI.getInstance().getColor() != Color.NONE) {
         doGoBack();
      }
   }

   public void setWindow(Window window) {
      this.window = window;
   }
}
