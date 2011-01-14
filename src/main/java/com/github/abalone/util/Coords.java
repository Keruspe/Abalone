package com.github.abalone.util;

import java.io.Serializable;

/**
 *
 * @author keruspe
 */
public class Coords implements Serializable {
   private Integer row;
   private Integer col;

   public Coords(Integer row, Integer col) {
      this.row = row;
      this.col = col;
   }

   public Integer getRow() {
      return this.row;
   }

   public Integer getCol() {
      return this.col;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Coords other = (Coords) obj;
      if (this.row != other.row && (this.row == null || !this.row.equals(other.row))) {
         return false;
      }
      if (this.col != other.col && (this.col == null || !this.col.equals(other.col))) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      return this.row * 10 + this.col;
   }
}
