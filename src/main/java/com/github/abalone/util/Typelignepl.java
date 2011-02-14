package com.github.abalone.util;

import java.util.ArrayList;

/**
 *
 * @author joe
 */
public enum Typelignepl {

   DIAGONAL1, // => "/"
   DIAGONAL2, //=> "\"
   HORIZONTAL;      // =>"--"

   public static ArrayList<Direction> lesDirectionParallèle(Typelignepl t) {
      ArrayList<Direction> lesdirections = new ArrayList<Direction>();
      if (t == Typelignepl.DIAGONAL1) {
         lesdirections.add(Direction.UPRIGHT);
         lesdirections.add(Direction.DOWNLEFT);
      } else if (t == Typelignepl.DIAGONAL2) {
         lesdirections.add(Direction.UPLEFT);
         lesdirections.add(Direction.DOWNRIGHT);

      } else if (t == Typelignepl.HORIZONTAL) {
         lesdirections.add(Direction.LEFT);
         lesdirections.add(Direction.RIGHT);
      }
      return lesdirections;
   }

   public static ArrayList<Direction> lesDirectionPerpendiculaire(Typelignepl t) {
      ArrayList<Direction> lesdirections = new ArrayList<Direction>();
      if (t == Typelignepl.DIAGONAL1) {
         lesdirections.add(Direction.LEFT);
         lesdirections.add(Direction.RIGHT);
         lesdirections.add(Direction.DOWNRIGHT);
         lesdirections.add(Direction.UPLEFT);
      } else if (t == Typelignepl.DIAGONAL2) {
         lesdirections.add(Direction.DOWNLEFT);
         lesdirections.add(Direction.LEFT);
         lesdirections.add(Direction.RIGHT);
         lesdirections.add(Direction.UPRIGHT);
      } else if (t == Typelignepl.HORIZONTAL) {
         lesdirections.add(Direction.UPLEFT);
         lesdirections.add(Direction.UPRIGHT);
         lesdirections.add(Direction.DOWNRIGHT);
         lesdirections.add(Direction.DOWNLEFT);
      }
      return lesdirections;

   }
}
