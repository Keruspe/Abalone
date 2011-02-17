package com.github.abalone.util;

/**
 *
 * @author joe
 */
public enum Direction {

   UPLEFT,
   UPRIGHT,
   LEFT,
   RIGHT,
   DOWNLEFT,
   DOWNRIGHT;

   public Direction reversed()
   {
      switch ( this )
      {
          case DOWNLEFT:
             return UPLEFT;
          case DOWNRIGHT:
             return UPRIGHT;
          case UPLEFT:
             return DOWNLEFT;
          case UPRIGHT:
             return DOWNRIGHT;
          default:
             return this;
      }
   }

}
