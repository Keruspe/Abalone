package com.github.abalone.util;

import java.io.Serializable;

/**
 *
 * @author keruspe
 */
/**
 * The color of a Ball
 */
public enum Color implements Serializable {

   /** A while Ball */
   WHITE,
   /** A black Ball */
   BLACK,
   /** No Ball */
   NONE,
   /** Out of the Board */
   INVALID;

   public Boolean isPlayer() {
      return (this == Color.WHITE || this == Color.BLACK);
   }

   public Color other() {
       if ( this == WHITE )
           return BLACK;
       else if ( this == BLACK )
           return WHITE;
       else
           return this;
   }

}
