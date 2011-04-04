package com.github.abalone.controller;

import com.github.abalone.util.NetworkStatus;
import com.github.abalone.view.Window;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sardemff7
 */
abstract class NetworkController implements Runnable
{
    protected final Window window;
    protected Socket socket;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;
    
    NetworkController(Window window)
    {
        this.window = window;
        this.window.addNetworkUI();
    }

    @Override
    public void run()
    {
        this.window.setNetworkStatus(NetworkStatus.WAITING_CONNECTION);
        this.openSocket();

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
                    //this.window.msg(line.substring(4));
                }
                System.out.println(line);
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
            } catch (IOException ex) {
                Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.window.lock();
        this.window.setNetworkStatus(NetworkStatus.WAITING_MOVE);
    }

    abstract protected void openSocket();
}
