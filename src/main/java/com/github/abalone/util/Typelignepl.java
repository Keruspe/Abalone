package com.github.abalone.util;

import java.util.ArrayList;

/**
 *
 * @author joe
 */
public enum Typelignepl {

   DIAGONAL1, // => "/"
   DIAGONAL2, //=> "\"
   HORIZONTAL, // =>"--"
   NONADJACENT;

   public static ArrayList<Direction> lesDirectionParallèle(Typelignepl t) {
      ArrayList<Direction> lesdirections = new ArrayList<Direction>();
      switch (t) {
         case DIAGONAL1:
            lesdirections.add(Direction.UPRIGHT);
            lesdirections.add(Direction.DOWNLEFT);
            break;
         case DIAGONAL2:
            lesdirections.add(Direction.UPLEFT);
            lesdirections.add(Direction.DOWNRIGHT);
            break;
         case HORIZONTAL:
            lesdirections.add(Direction.LEFT);
            lesdirections.add(Direction.RIGHT);
            break;
      }
      return lesdirections;
   }

   public static ArrayList<Direction> lesDirectionPerpendiculaire(Typelignepl t) {
      ArrayList<Direction> lesdirections = new ArrayList<Direction>();
      switch (t) {
         case DIAGONAL1:
            lesdirections.add(Direction.LEFT);
            lesdirections.add(Direction.RIGHT);
            lesdirections.add(Direction.DOWNRIGHT);
            lesdirections.add(Direction.UPLEFT);
            break;
         case DIAGONAL2:
            lesdirections.add(Direction.DOWNLEFT);
            lesdirections.add(Direction.LEFT);
            lesdirections.add(Direction.RIGHT);
            lesdirections.add(Direction.UPRIGHT);
            break;
         case HORIZONTAL:
            lesdirections.add(Direction.UPLEFT);
            lesdirections.add(Direction.UPRIGHT);
            lesdirections.add(Direction.DOWNRIGHT);
            lesdirections.add(Direction.DOWNLEFT);
            break;
      }
      return lesdirections;

   }
}
