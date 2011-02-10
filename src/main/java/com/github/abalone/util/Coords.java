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

   public Coords moveTo( Direction direction){
       // construction de la coordonee d arrivee
       Coords arrivee = new Coords(0, 0);

       if(direction == Direction.UPLEFT){

           if (this.getRow()>1){
                arrivee.row =this.getRow()+1;
                arrivee.col = this.getCol();
            }
            else{
                arrivee.row = this.getRow()+1;
                arrivee.col = this.getCol()-1;
            }
           }
           if(direction == Direction.UPRIGHT){
                if (this.getRow()>1){
                    arrivee.row = this.getRow()+1;
                    arrivee.col = this.getCol()+1;
                }else{
                    arrivee.row = this.getRow()+1;
                    arrivee.col = this.getCol();
                }
            }
            if(direction == Direction.LEFT){
                arrivee.row = this.getRow();
                arrivee.col = this.getCol()-1;
            }
            if(direction == Direction.RIGHT){

                arrivee.row = this.getRow();
                arrivee.col = this.getCol()+1;
            }
            if(direction == Direction.DOWNLEFT){

           if (this.getRow()>1){
                arrivee.row = this.getRow()-1;
                arrivee.col = this.getCol()-1;
            }
            else{
                arrivee.row = this.getRow()-1;
                arrivee.col = this.getCol();
            }
           }
           if(direction == Direction.DOWNRIGHT){
                if (this.getRow()>1){
                    arrivee.row = this.getRow()+1;
                    arrivee.col = this.getCol();
                }else{
                   arrivee.row = this.getRow()+1;
                   arrivee.col = this.getCol();
                }
     }
     return arrivee;

   }
   
   @Override
   public int hashCode() {
      return this.row * 10 + this.col;
   }
}
