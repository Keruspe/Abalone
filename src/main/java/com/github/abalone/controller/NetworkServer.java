package com.github.abalone.controller;

import com.github.abalone.config.Config;
import com.github.abalone.view.Window;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sardemff7
 */
public class NetworkServer extends NetworkController
{
    NetworkServer(Window window)
    {
        super(window);
    }

    @Override
    protected void openSocket()
    {
        ServerSocket server;
        try {
            server = new ServerSocket((Integer) Config.get("port"));
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try
        {
            this.socket = server.accept();
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
            this.input = new ObjectInputStream(this.socket.getInputStream());
        }
        catch (IOException ex)
        {
        }
    }
}
