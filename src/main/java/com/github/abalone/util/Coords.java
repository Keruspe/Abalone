package com.github.abalone.util;

import com.github.abalone.elements.Typelignepl;
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

    public Coords(Coords other) {
        this.row = other.row;
        this.col = other.col;
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
      int hash = 5;
      hash = 83 * hash + (this.row != null ? this.row.hashCode() : 0);
      hash = 83 * hash + (this.col != null ? this.col.hashCode() : 0);
      return hash;
   }

    public Coords moveTo(Direction direction){
        // construction de la coordonee d arrivee
        Coords arrivee = new Coords(this);

        switch ( direction )
        {
        case UPLEFT:
            ++arrivee.row;
            if ( this.row < 1 )
                --arrivee.col;
        break;
        case UPRIGHT:
            ++arrivee.row;
            if ( this.row > 0 )
                ++arrivee.col;
        break;
        case LEFT:
            --arrivee.col;
        break;
        case RIGHT:
            ++arrivee.col;
        break;
        case DOWNLEFT:
            --arrivee.row;
            if ( this.row < 1 )
                --arrivee.col;
        break;
        case DOWNRIGHT:
            ++arrivee.row;
            if ( this.row > 0 )
                ++arrivee.col;
        break;
        }
    return arrivee;
    }

    //c est juste a cote de de l'objet courant
    public Typelignepl LignePl(Coords c){
        if(this.moveTo(Direction.UPLEFT).equals(c)||c.equals(this.moveTo(Direction.DOWNRIGHT))){
            return Typelignepl.DIAGONAL2;
        } else if(this.moveTo(Direction.UPRIGHT).equals(c) || c.equals(this.moveTo(Direction.DOWNLEFT))) {
           return Typelignepl.DIAGONAL1;
        } else
            return Typelignepl.HORIZONTAL;
    }

    /**
     * @param row the row to set
     */
    public void setRow(Integer row) {
        this.row = row;
    }

    /**
     * @param col the col to set
     */
    public void setCol(Integer col) {
        this.col = col;
    }
}
