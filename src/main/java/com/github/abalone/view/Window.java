package com.github.abalone.view;

import com.github.abalone.controller.Game;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JFrame;

/**
 *
 * @author sardemff7
 */
public class Window extends JFrame {
    
    private final Tools tools;
    private final Board board;
    private final Game controller;
    
    public Window()
    {
        super("Abalone");

        this.controller = Game.getInstance();
        this.controller.setWindow(this);

        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(false);
        
        LayoutManager layout = new BorderLayout();
        this.setLayout(layout);

        this.tools = new Tools();
        this.add(this.tools, BorderLayout.PAGE_START);

        this.board = new Board();
        this.add(this.board);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
        this.board.computeBoardScale(true);
    }

    public void updateBoard()
    {
        this.board.repaint();
    }
}
