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

   public Set<Direction> validDirections(Set<Coords> selectedBallsCoords) {
      return this.game.getBoard().validDirectionsCoords(selectedBallsCoords, this.game.getTurn());
   }

   public GameState doMove(Set<Coords> selectedBallsCoords, Direction direction, Boolean AITurn) {
      Color current = this.game.getTurn();
      if (current == Color.NONE) {
         return GameState.OUTOFTURNS;
      } else if(this.game.over()) {
         return GameState.WON;
      /*
       * FIXME: must check if we actually have an AI
      } else if ((current == Color.BLACK) && !AITurn) {
         return GameState.RUNNING;
       */
      }
      Move move = this.game.getBoard().getValidMoveCoords(selectedBallsCoords, direction, current);
      if (move != null) {
         this.game.getBoard().apply(move);
         this.game.addToHistory(move);
         Move bestMove = AI.getInstance().getBestMove(this.game.getNextTurn());
         if (!bestMove.isAIMove()) {
            this.currentBestMove = bestMove;
         }
         this.window.updateBoard(this.game.getTurn());
      }
      return GameState.RUNNING;
   }

   public GameState move(Set<Coords> selectedBallsCoords, Direction direction) {
      return doMove(selectedBallsCoords, direction, Boolean.FALSE);
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
