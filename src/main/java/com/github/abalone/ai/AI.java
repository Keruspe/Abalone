package com.github.abalone.ai;

import com.github.abalone.controller.GameController;
import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Game;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.util.Move;
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
      Set<Ball> balls = this.game.getBoard().getBalls();
      for (Ball b : balls) {
         if (b.getColor() != current) {
            continue;
         }
         Set<Coords> coords = new HashSet<Coords>();
         coords.add(b.getCoords());
         for ( Direction d : Direction.values() ) {
            bestMove = this.game.getBoard().getMove(coords, d, current);
            if ( bestMove != null )
               break;
         }
         if ( bestMove != null )
            break;
      }
      if (current == selfColor) {
         GameController.getInstance().doMove(bestMove, Boolean.TRUE);
         return null;
      }
      return bestMove;
   }

   public Color getColor() {
      return selfColor;
   }

}
