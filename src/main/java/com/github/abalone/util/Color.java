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
}
