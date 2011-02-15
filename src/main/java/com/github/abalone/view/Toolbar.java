package com.github.abalone.view;

import javax.swing.JToolBar;

/**
 *
 * @author sardemff7
 */
class Toolbar extends JToolBar
{
    public Toolbar(Board board)
    {
        this.add(new ToolButton("new-game"));

        this.add(new ToolButton("save-game"));

        this.add(new ToolButton("load-game"));

        this.add(new ToolButton("best-move", board));

        this.add(new ToolButton("undo"));

        this.add(new ToolButton("preferences"));
        
        this.add(new ToolButton("quit"));
    }
}
