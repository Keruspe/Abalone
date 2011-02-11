package com.github.abalone.elements;

import com.github.abalone.util.Color;
import java.util.ArrayList;

/**
 *
 * @author joe
 */
public class Partie {
    private Board board;
    private Color turn;
    private Integer tempsRestant;
    private Integer nbToursRestant;
    private ArrayList<Mouvement> historique;

    public Partie(Color turn, Integer time, Integer turns){
        this.board = Board.getInstance();
        this.turn = turn;
        this.tempsRestant = time;
        this.nbToursRestant = turns;
    }

    public ArrayList<Mouvement> getHistorique()
    {
        return historique;
    }

    public Integer getNbToursRestant()
    {
        return nbToursRestant;
    }

    public Integer getTempsRestant()
    {
        return tempsRestant;
    }

    public Board getBoard()
    {
        return this.board;
    }
    
    public Color getTurn()
    {
        return this.turn;
    }

}
