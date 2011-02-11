package com.github.abalone.view;

import javax.swing.JToolBar;

/**
 *
 * @author sardemff7
 */
class Toolbar extends JToolBar
{
    public Toolbar()
    {
        this.add(new Button("new-game"));

        this.add(new Button("save-game"));

        this.add(new Button("load-game"));
        
        this.add(new Button("preferences"));
        
        this.add(new Button("quit"));
    }
}
