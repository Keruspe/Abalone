/*
 * Abalone.java
 */

package com.github.abalone;

import com.github.abalone.view.Window;

/**
 * The main class of the application.
 */
public class Abalone {

    private Abalone()
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
        Abalone app = new Abalone();
        app.launch();

    }

    private Window view;
}
