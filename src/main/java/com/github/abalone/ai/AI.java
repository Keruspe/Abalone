package com.github.abalone.ai;

import com.github.abalone.controller.GameController;
import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Game;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.controller.Move;
import com.github.abalone.elements.Board;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author keruspe
 */
public class AI {

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
      AI.instance.getBestMove(Color.WHITE);
   }

   public static AI getInstance() {
      return AI.instance;
   }

   public Move getBestMove(Color current) {
      Move bestMove = null;
      Board board = this.game.getBoard();
      Set<Ball> balls = board.getBalls();
      for (Ball b : balls) {
         if (b.getColor() != current) {
            continue;
         }
         Set<Coords> coords = new HashSet<Coords>();
         coords.add(b.getCoords());
         for ( Direction d : Direction.values() ) {
            bestMove = null;
            bestMove = new Move(board.getLineColorBallsAt(coords, current), d, current);
            bestMove.compute(board);
            if ( bestMove.isValid() )
               break;
         }
         if ( bestMove != null )
            break;
      }
      return bestMove;
   }

   public Color getColor() {
      return selfColor;
   }

}
