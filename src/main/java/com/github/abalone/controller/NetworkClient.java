package com.github.abalone.controller;

import com.github.abalone.config.Config;
import com.github.abalone.view.Window;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sardemff7
 */
class NetworkClient extends NetworkController
{
    NetworkClient(Window window)
    {
        super(window);
        this.window.lock();
    }

    @Override
    protected void openSocket()
    {
        try {
            this.socket = new Socket(InetAddress.getByName((String) Config.get("host")), (Integer) Config.get("port"));
            this.input = new ObjectInputStream(this.socket.getInputStream());
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
