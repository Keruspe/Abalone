/*
 * Abalone.java
 */

package com.github.abalone;

import com.github.abalone.controller.Game;
import com.github.abalone.view.Window;

/**
 * The main class of the application.
 */
public class Abalone {
    private final Game controller;
    private final Window view;

    private Abalone()
    {
        this.controller = new Game();
        this.view = new Window(this.controller);
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
}
