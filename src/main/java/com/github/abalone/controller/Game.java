package com.github.abalone.controller;

import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Board;
import com.github.abalone.elements.Partie;
import com.github.abalone.elements.Mouvement;
import com.github.abalone.elements.Typelignepl;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.view.Window;
import java.util.HashSet;
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
    private Partie p;

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

    public Color adversaire() {
        Color adversaire = Color.NONE;
        if (turn == Color.BLACK) {
            adversaire = Color.WHITE;
        } else if (turn == Color.WHITE) {
            adversaire = Color.BLACK;
        }
        return adversaire;
    }
    //renvoi la coordonnée de la bille la plus proche de la bille adverse ou de la case vide
    public Coords closer(Set<Coords> selectedCoords, Direction to) {
        Iterator itc = selectedCoords.iterator();
        Coords closer = new Coords(10, 10);
        if (to == Direction.DOWNLEFT || to == Direction.DOWNRIGHT) {
            closer.setRow(10);
            while (itc.hasNext()) {
                Coords c = (Coords) itc.next();
                if (closer.getRow() > c.getRow()) {
                    closer = c;
                }
            }
        }
        if (to == Direction.UPLEFT || to == Direction.UPRIGHT) {
            closer.setRow(-10);
            while (itc.hasNext()) {
                Coords c = (Coords) itc.next();
                if (closer.getRow() < c.getRow()) {
                    closer = c;
                }
            }
        }
        if (to == Direction.LEFT) {
            closer.setCol(10);
            while (itc.hasNext()) {
                Coords c = (Coords) itc.next();
                if (closer.getCol() > c.getCol()) {
                    closer = c;
                }
            }
        }
        if (to == Direction.RIGHT) {
            closer.setCol(-10);
            while (itc.hasNext()) {
                Coords c = (Coords) itc.next();
                if (closer.getCol() < c.getCol()) {
                    closer = c;
                }
            }
        }
        return closer;

    }

    public Boolean coupValide(Set<Coords> selectedBalls,Direction direction){
            Iterator itc=selectedBalls.iterator();
            /*deplacement d'une bille*/
            Boolean result = false;

            switch ( selectedBalls.size() )
            {
            case 1:
                Coords c;
                c = (Coords) itc.next();
                if (this.board.elementAt(c.moveTo(direction)) == Color.NONE
                        && this.board.elementAt(c.moveTo(direction)) != Color.INVALID) {
                    result = true;
                }
            break;
            case 2:
                Coords c21 = (Coords) itc.next();
                Coords c22 = (Coords) itc.next();
                if (Typelignepl.lesDirectionPerpendiculaire(c21.LignePl(c22)).contains(direction)) {
                    if (this.board.elementAt(c21.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c21.moveTo(direction)) != Color.INVALID
                            && this.board.elementAt(c22.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c22.moveTo(direction)) != Color.INVALID) {
                        result = true;
                    }
                } else {
                    Coords closer = closer(selectedBalls, direction);
                    Coords next = closer.moveTo(direction);
                    if (p.getBoard().elementAt(next) == Color.NONE) {
                        result = true;
                    } else if (p.getBoard().elementAt(next) == this.adversaire()) {
                        if (p.getBoard().elementAt(next.moveTo(direction)) == Color.NONE
                                || p.getBoard().elementAt(next.moveTo(direction)) == Color.INVALID) {
                            return true;
                        }
                    }
                }
            break;
            case 3:
                // billes supposées sur la meme ligne
                Coords c31 = (Coords) itc.next();
                Coords c32 = (Coords) itc.next();
                Coords c33 = (Coords) itc.next();
                if (Typelignepl.lesDirectionPerpendiculaire(c31.LignePl(c32)).contains(direction)) {
                    if (this.board.elementAt(c31.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c31.moveTo(direction)) != Color.INVALID
                            && this.board.elementAt(c32.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c32.moveTo(direction)) != Color.INVALID
                            && this.board.elementAt(c33.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c33.moveTo(direction)) != Color.INVALID) {
                        result = true;
                    } else {
                        Coords closer = closer(selectedBalls, direction);
                        Coords next1 = closer.moveTo(direction);
                        Coords next2 = next1.moveTo(direction);
                        Coords next3 = next2.moveTo(direction);
                        if (this.board.elementAt(next1.moveTo(direction)) == Color.NONE) {
                            result = true;
                        } else if (this.board.elementAt(next1.moveTo(direction)) == this.adversaire()) {
                            if (this.board.elementAt(next2.moveTo(direction)) == Color.NONE
                                    || this.board.elementAt(next2.moveTo(direction)) == Color.INVALID) {
                                result = true;
                            } else if (this.board.elementAt(next2.moveTo(direction)) == this.adversaire()) {
                                if (this.board.elementAt(next3.moveTo(direction)) == Color.NONE
                                        || this.board.elementAt(next3.moveTo(direction)) == Color.INVALID) {
                                    result = true;
                                }
                            }
                        }
                    }
                }
            break;
        }
        return result;
    }

    //fonction qui revois la liste des coordonné des conserne par un coup... si un coup n'est pas valide
    //la list revoiyée sera vide
    public Set<Coords> coupValid2(Set<Coords> selectedBalls, Direction direction) {
        Iterator itc = selectedBalls.iterator();
        Set<Coords> result = new HashSet<Coords>();
        switch (selectedBalls.size()) {
            case 1:
                Coords c;
                c = (Coords) itc.next();
                if (this.board.elementAt(c.moveTo(direction)) == Color.NONE
                        && this.board.elementAt(c.moveTo(direction)) != Color.INVALID) {
                    result.add(c);
                }
                break;
            case 2:
                Coords c21 = (Coords) itc.next();
                Coords c22 = (Coords) itc.next();
                if (Typelignepl.lesDirectionPerpendiculaire(c21.LignePl(c22)).contains(direction)) {
                    if (this.board.elementAt(c21.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c21.moveTo(direction)) != Color.INVALID
                            && this.board.elementAt(c22.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c22.moveTo(direction)) != Color.INVALID) {
                        result.add(c21);
                        result.add(c21);
                    }
                } else {
                    Coords closer = closer(selectedBalls, direction);
                    Coords next = closer.moveTo(direction);
                    if (p.getBoard().elementAt(next) == Color.NONE) {
                        result.add(c21);
                        result.add(c21);
                    } else if (p.getBoard().elementAt(next) == this.adversaire()) {
                        if (p.getBoard().elementAt(next.moveTo(direction)) == Color.NONE
                                || p.getBoard().elementAt(next.moveTo(direction)) == Color.INVALID) {
                            result.add(c21);
                            result.add(c21);
                            result.add(next);
                        }
                    }
                }
                break;
            case 3:
                // billes supposées sur la meme ligne
                Coords c31 = (Coords) itc.next();
                Coords c32 = (Coords) itc.next();
                Coords c33 = (Coords) itc.next();
                if (Typelignepl.lesDirectionPerpendiculaire(c31.LignePl(c32)).contains(direction)) {
                    if (this.board.elementAt(c31.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c31.moveTo(direction)) != Color.INVALID
                            && this.board.elementAt(c32.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c32.moveTo(direction)) != Color.INVALID
                            && this.board.elementAt(c33.moveTo(direction)) == Color.NONE
                            && this.board.elementAt(c33.moveTo(direction)) != Color.INVALID) {
                        result.add(c31);
                        result.add(c32);
                        result.add(c33);
                    } else {
                        Coords closer = closer(selectedBalls, direction);
                        Coords next1 = closer.moveTo(direction);
                        Coords next2 = next1.moveTo(direction);
                        Coords next3 = next2.moveTo(direction);
                        if (this.board.elementAt(next1.moveTo(direction)) == Color.NONE) {
                            result.add(c31);
                            result.add(c32);
                            result.add(c33);
                        } else if (this.board.elementAt(next1.moveTo(direction)) == this.adversaire()) {
                            if (this.board.elementAt(next2.moveTo(direction)) == Color.NONE
                                    || this.board.elementAt(next2.moveTo(direction)) == Color.INVALID) {
                                result.add(c31);
                                result.add(c32);
                                result.add(c33);
                                result.add(next1);
                            } else if (this.board.elementAt(next2.moveTo(direction)) == this.adversaire()) {
                                if (this.board.elementAt(next3.moveTo(direction)) == Color.NONE
                                        || this.board.elementAt(next3.moveTo(direction)) == Color.INVALID) {
                                    result.add(c31);
                                    result.add(c32);
                                    result.add(c33);
                                    result.add(next1);
                                    result.add(next2);
                                }
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }
   public void joueUncoup(Set<Coords> selectedBalls, Direction direction){
       /*
        * TODO: move the check to a "validMoves" function
        * that the view will call to display needed controls
        * (implies there will not be unvalid moves here)
        */
       Set ballTomove = coupValid2(selectedBalls, direction);
       if(!ballTomove.isEmpty()){
           this.board.move(selectedBalls, direction);
           nextTurn();
       }
   }

    public void setWindow(Window window)
    {
        this.window = window;
    }
}
