package com.github.abalone.controller;

import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Board;
import com.github.abalone.elements.Partie;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.view.Window;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author sardemff7
 */
public class Game
{
    private static Game singleton;
    private Color turn;
    private Board board;
    private Window window;
    
    private Game()
    {
        this.turn = Color.WHITE;
    }

    

    public static Game getInstance()
    {
        if ( Game.singleton == null )
            Game.singleton = new Game();
        return Game.singleton;
    }

    /// Launch a new game
    public void launch()
    {
        Board.getInstance().fill(null);
        this.window.updateBoard();
    }

    /// Save the game
    public void save()
    {
        Partie s = new Partie(this.turn, 0, 0);
        /*
         * TODO: serialize the save to the file
         */
    }

    /// Load the saved game
    public void load()
    {
        /*
         * TODO: deserialize form the file
         */
        this.window.updateBoard();
    }

    /// Quit the game
    public void quit()
    {
        System.exit(0);
    }


    /// Next turn
    public void nextTurn()
    {
        this.turn = (this.turn.equals(Color.BLACK)) ? Color.WHITE : Color.BLACK;
    }

    /// Do a move
    /**
     * Check if the wanted move is correct and what
     * effect will it have
     *
     * @param coords The list of coordinates of the balls to move
     * @param direction The direction in which we want to move the balls
     */
    public void move(Set <Coords> coords, Direction direction) {
        Coords c;
        Iterator itC=coords.iterator();
        Iterator itB= this.board.getBalls().iterator();
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

            switch ( selectedBalls.size() )
            {
                case 1:
                    Coords c;
                    c=(Coords) itc.next();
                    if(this.board.elementAt(c.moveTo(direction))==Color.NONE){
                           return true;
                    }
                break;
            }

            return false;
    }

    public void setWindow(Window window)
    {
        this.window = window;
    }
}
