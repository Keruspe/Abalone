package com.github.abalone.controller;

import com.github.abalone.util.listeners.MessageListener;
import com.github.abalone.util.NetworkStatus;
import com.github.abalone.view.NetworkUI;
import com.github.abalone.view.Window;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sardemff7
 */
abstract class NetworkController implements Runnable, MessageListener
{
    protected final Window window;
    private final NetworkUI networkUI;
    protected Socket socket;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;
    HashSet<MessageListener> listeners = new HashSet<MessageListener>();
    
    NetworkController(Window window)
    {
        this.window = window;
        this.networkUI = this.window.addNetworkUI();
    }

    @Override
    public void run()
    {
        this.window.setNetworkStatus(NetworkStatus.WAITING_CONNECTION);
        this.openSocket();


        this.networkUI.setVisible(true);
        this.networkUI.addMessageListener(this);
        this.addMessageListener(this.networkUI);
        this.window.setNetworkStatus(NetworkStatus.CONNECTED);
        GameController controller = GameController.getInstance();
        controller.launch();
        try
        {
            String line;
            while ( this.socket.isConnected() )
            {
                line = this.input.readUTF();
                if ( line.startsWith("MOVE") )
                {
                    Move move = null;
                    try {
                        move = (Move) this.input.readObject();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    controller.doMove(move);
                    this.window.unlock();
                }
                else if(line.startsWith("MSG "))
                {
                    for ( MessageListener l : this.listeners )
                        l.newMessage(line.substring(4));
                }
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void doMove(Move move)
    {
        if (move != null)
        {
            try {
                this.output.writeUTF("MOVE");
                this.output.writeObject(move);
                this.output.flush();
            } catch (IOException ex) {
                Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.window.lock();
        this.window.setNetworkStatus(NetworkStatus.WAITING_MOVE);
    }

    @Override
    public void newMessage(String message)
    {
        try {
            this.output.writeUTF("MSG " + message);
            this.output.flush();
        } catch (IOException ex) {
            Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    abstract protected void openSocket();

    public void addMessageListener(MessageListener listener)
    {
        this.listeners.add(listener);
    }
}
