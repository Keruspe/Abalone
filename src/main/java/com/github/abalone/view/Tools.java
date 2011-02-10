package com.github.abalone.view;

import javax.swing.JToolBar;

/**
 *
 * @author sardemff7
 */
class Tools extends JToolBar
{
    public Tools(Window window)
    {
        this.add(new Button(window, "new-game"));

        this.add(new Button(window, "save-game"));

        this.add(new Button(window, "load-game"));
        
        this.add(new Button(window, "preferences"));
        
        this.add(new Button(window, "quit"));
    }
}
