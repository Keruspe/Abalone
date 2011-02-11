package com.github.abalone.view;

import com.github.abalone.controller.Game;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author sardemff7
 */
public class Window extends JFrame {
    
    private final Toolbar tools;
    private final Board board;
    private final Game controller;
    
    public Window()
    {
        super("Abalone");

        this.controller = Game.getInstance();
        this.controller.setWindow(this);

        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(false);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        LayoutManager layout = new BorderLayout();
        this.setLayout(layout);

        this.tools = new Toolbar();
        this.add(this.tools, BorderLayout.PAGE_START);

        this.board = new Board();
        this.add(this.board);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void updateBoard()
    {
        this.board.repaint();
    }
}
