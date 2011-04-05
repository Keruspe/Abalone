package com.github.abalone.view;

import com.github.abalone.controller.GameController;
import com.github.abalone.util.Color;
import com.github.abalone.util.NetworkStatus;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author sardemff7
 */
public class Window extends JFrame implements ComponentListener
{
    
    private final Toolbar toolbar;
    private final Board board;
    private Boolean locked = Boolean.FALSE;
    private final JLabel status;
    private NetworkUI networkUI;

    Boolean isLocked() {
        return this.locked;
    }

    public void lock() {
        this.locked = Boolean.TRUE;
    }

    public void unlock() {
        this.locked = Boolean.FALSE;
    }
    
    public Window() throws Exception
    {
        super("Abalone");

        GameController.getInstance().setWindow(this);

        this.setSize(300, 100);

        String[] lookAndFeels = {
            "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
	    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
            "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel",
            "com.sun.java.swing.plaf.motif.MotifLookAndFeel",
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

        this.board = new Board(this);
        this.toolbar = new Toolbar(this.board);
        this.status = new JLabel("Abalone");

        this.add(this.toolbar, BorderLayout.PAGE_START);
        this.add(this.board);
        this.add(this.status, BorderLayout.PAGE_END);

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

    public void updateBoard(Color turn)
    {
	this.board.reversed = turn.equals(Color.WHITE);
        this.board.repaint();
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        this.board.computeBoardScale();
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

    public NetworkUI addNetworkUI()
    {
        this.networkUI = new NetworkUI(this);
        return this.networkUI;
    }

    public void setNetworkStatus(NetworkStatus networkState)
    {
        switch ( networkState )
        {
            case CONNECTED:
                this.status.setText("Now connected");
            break;
            case WAITING_CONNECTION:
                this.status.setText("Waiting for connection");
            break;
            case WAITING_MOVE:
                this.status.setText("Waiting for move");
            break;
            case CONNECTION_ERROR:
                this.status.setText("Connection problem, can't continue");
            break;
            case QUIT:
                this.status.setText("Your opponent left the game");
            break;
        }
    }
}
