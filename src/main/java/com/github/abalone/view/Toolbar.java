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
        this.add(new ToolButton("new-game"));

        this.add(new ToolButton("save-game"));

        this.add(new ToolButton("load-game"));
        
        this.add(new ToolButton("preferences"));
        
        this.add(new ToolButton("quit"));
    }
}
