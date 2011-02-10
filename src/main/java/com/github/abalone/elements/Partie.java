/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.abalone.elements;

import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author joe
 */
public class Partie {
    private Board theBoard;
    private Color aQuiletours;
    private Integer tempsRrestant;
    private Integer nbTouRestant;
    private ArrayList<Mouvement> historique;
    public Partie(Board b, Integer tr, Integer nbT){
        theBoard=b;
        aQuiletours=Color.WHITE;
    }
    /**
     * @return the theBoard
     */
    public Board getBoard() {
        return theBoard;
    }
    public Color getaQuiletours(){
        return aQuiletours;
    }
    public void setaQuiletours(){
        if(aQuiletours.equals(Color.BLACK)){

    }
    }
    /**
     * @param theBoard the theBoard to set
     */
    public void setBoard(Set <Coords> aCoords, String direction) {
        Coords c;
        Iterator itC=aCoords.iterator();
        Iterator itB= this.getBoard().getBalls().iterator();
        /*Je cherche la bille correspondante à cette coordonnée*/
        boolean trouverB;
        while(itC.hasNext()){//parcout des coordonnées
            c=(Coords) itC.next();
            trouverB = false;
            // premier  while : parcours des billes --> itB
            while(itB.hasNext() && !trouverB){//parcourt des bille du plateau
                Ball ball = (Ball) itC.next();
                if(ball.getCoords().equals(c))
                {
                   trouverB = true;
                   Ball b = (Ball) itC.next();
                   b.move(direction);
                }
            }
        }
    }
    /*Pas optimiser et pas finit non plus*/
    public boolean coupValide(Set<Coords> selectedBalls,Direction direction){
            Iterator itc=selectedBalls.iterator();
            /*deplacement d'une bille*/
               Coords c;
            for(int i=0;i<selectedBalls.size();i++){
                c=(Coords) itc.next();
                
                if(this.theBoard.elementAt(c.moveTo(c, direction))==Color.NONE){
                       return true;
                }
            }


        return false;
    }


}
