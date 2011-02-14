package com.github.abalone.ai;

import com.github.abalone.elements.Game;
import com.github.abalone.util.Move;

/**
 *
 * @author keruspe
 */
public class AI {

   private static AI instance;
   private Game game;

   private AI(Game game) {
      this.game = game;
   }

   public static AI init(Game game) {
      if (AI.instance == null)
         AI.instance = new AI(game);
      return AI.instance;
   }

   public static AI getInstance() {
      return AI.instance;
   }

   public Move getBestMove() {
      return null;
   }

}
