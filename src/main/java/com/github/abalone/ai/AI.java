package com.github.abalone.ai;

import com.github.abalone.controller.GameController;
import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Board;
import com.github.abalone.elements.Game;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.util.Move;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author keruspe
 * @author sardemff7
 */
public class AI {

   private static final Integer MAX_DEPTH = 1;

   private static AI instance;
   private Game game;
   private Color selfColor;

   private AI(Game game, Color selfColor) {
      this.game = game;
      this.selfColor = selfColor;
   }

   public static void init(Game game, Color AIColor) {
      if (AI.instance == null)
         AI.instance = new AI(game, AIColor);
   }

   public static AI getInstance() {
      return AI.instance;
   }

   public Move getBestMove(Color current) {
      Board board = new Board(this.game.getBoard());
      Move bestMove = null;
      Integer best = -100;
      Set<Move> moves = board.getPossibleMoves(current);
      GameController.getInstance().repaint();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
        }
      for ( Move m : moves ) {
         board.apply(m);
         Integer score = negaMax(board, current.other(), MAX_DEPTH - 1);
         if ( score > best ) {
             best = score;
             bestMove = m;
         }
         board.revert(m);
      }
      if (current == selfColor) {
         bestMove.setIsAIMove();
         board.apply(bestMove);
      }
      return bestMove;
   }

   private Integer negaMax(Board board, Color current, Integer depth) {
      if ( depth > 0 ) {
         Integer best = -100;
         Set<Move> moves = board.getPossibleMoves(current);
         for ( Move m : moves ) {
            board.apply(m);
            Integer score = negaMax(board, current.other(), depth - 1);
            if ( score > best )
                best = score;
            board.revert(m);
         }
         return best;
      } else {
          return ( ( current == selfColor ) ? 1 : -1 ) * this.evaluateBoard(board, current);
      }
   }

   private Integer evaluateBoard(Board board, Color player) {
      Integer good = board.ballsCount(player);
      Integer bad = board.ballsCount(player.other());
      return good - bad;
   }

   public Color getColor() {
      return selfColor;
   }

}
