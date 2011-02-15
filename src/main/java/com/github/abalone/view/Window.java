package com.github.abalone.view;

import com.github.abalone.controller.GameController;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author sardemff7
 */
public class Window extends JFrame implements ComponentListener
{
    
    private final Toolbar tools;
    private final Board board;
    private final GameController controller;
    
    public Window() throws Exception
    {
        super("Abalone");

        this.controller = GameController.getInstance();
        this.controller.setWindow(this);

        this.setSize(800, 600);

        String[] lookAndFeels = {
            "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
            "com.sun.java.swing.plaf.motif.MotifLookAndFeel",
            "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel",
            "javax.swing.plaf.metal.MetalLookAndFeel"
        };

        integrate: {
        for ( String name: lookAndFeels )
        {
            if ( this.checkLookAndFeel(name) )
                break integrate;
        }
        throw new Exception("No LookAndFeel");
        }
        
        LayoutManager layout = new BorderLayout();
        this.setLayout(layout);

        this.tools = new Toolbar();
        this.add(this.tools, BorderLayout.PAGE_START);

        this.board = new Board();
        this.add(this.board);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addComponentListener(this);
    }

    private Boolean checkLookAndFeel(String name)
    {
        try {
            UIManager.setLookAndFeel(name);
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void updateBoard()
    {
        this.board.repaint();
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        this.board.computeBoardScale(Boolean.TRUE);
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
    }

    @Override
    public void componentShown(ComponentEvent ce) {
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
    }
}
