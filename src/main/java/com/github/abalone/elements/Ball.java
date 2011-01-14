package com.github.abalone.elements;

import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import java.io.Serializable;

/**
 *
 * @author keruspe
 */
public class Ball implements Serializable {
   private Color color;
   private Coords coords;

   public Ball(Color color, Integer row, Integer col) {
      this.color = color;
      this.coords = new Coords(row, col);
   }

   public Color getColor() {
      return this.color;
   }

   public Coords getCoords() {
      return this.coords;
   }

   public void setCoords(Coords coords) {
      this.coords = coords;
   }

   public void SetCoords(Integer row, Integer col) {
      this.coords = new Coords(row, col);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Ball other = (Ball) obj;
      if (this.coords != other.coords && (this.coords == null || !this.coords.equals(other.coords))) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return this.coords.hashCode();
   }
}
