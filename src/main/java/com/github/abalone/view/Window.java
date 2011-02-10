package com.github.abalone.view;

import com.github.abalone.controller.Game;
import com.kitfox.svg.SVGUniverse;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
    
    public Window(Game controller)
    {
        super("Abalone");

        this.controller = controller;

        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(false);
        
        LayoutManager layout = new BorderLayout();
        this.setLayout(layout);

        this.tools = new Tools(this);
        this.add(this.tools, BorderLayout.PAGE_START);

        this.board = new Board(this);
        this.add(this.board);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
