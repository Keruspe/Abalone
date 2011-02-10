package com.github.abalone.view;

import javax.swing.JToolBar;

/**
 *
 * @author sardemff7
 */
class Tools extends JToolBar
{
    private final Window window;

    public Tools(Window window)
    {
        this.window = window;

        Button b;

        b = new Button(this.window, "new-game");
        this.add(b);
        b.addActionListener(b);
        
        b = new Button(this.window, "load-game");
        this.add(b);
        b.addActionListener(b);
        
        b = new Button(this.window, "preferences");
        this.add(b);
        b.addActionListener(b);
        
        b = new Button(this.window, "quit");
        this.add(b);
        b.addActionListener(b);
    }
}
