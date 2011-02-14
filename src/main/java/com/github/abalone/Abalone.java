/*
 * Abalone.java
 */

package com.github.abalone;

import com.github.abalone.view.Window;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class of the application.
 */
public class Abalone {
    private final Window view;

    private Abalone() throws Exception
    {
        this.view = new Window();
    }

    private void launch()
    {
        view.setVisible(true);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args)
    {
        Abalone app;
        try {
            app = new Abalone();
            app.launch();
        } catch (Exception ex) {
            Logger.getLogger(Abalone.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
