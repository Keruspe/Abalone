package com.github.abalone.view;

import javax.swing.JToolBar;

/**
 *
 * @author sardemff7
 */
class Toolbar extends JToolBar
{
    private final ToolButton newGame;
    private final ToolButton hostGame;
    private final ToolButton joinGame;
    private final ToolButton saveGame;
    private final ToolButton loadGame;
    private final ToolButton bestMove;
    private final ToolButton undo;

    public Toolbar(Board board)
    {
        this.newGame = new ToolButton("new-game", this);
        this.add(this.newGame);

        this.hostGame = new ToolButton("host-game", this);
        this.add(this.hostGame);

        this.joinGame = new ToolButton("join-game", this);
        this.add(this.joinGame);

        this.saveGame = new ToolButton("save-game");
        this.saveGame.setEnabled(false);
        this.add(this.saveGame);

        this.loadGame = new ToolButton("load-game", this);
        this.add(this.loadGame);

        this.bestMove = new ToolButton("best-move", board);
        this.bestMove.setEnabled(false);
        this.add(this.bestMove);

        this.undo = new ToolButton("undo");
        this.undo.setEnabled(false);
        this.add(this.undo);

        this.add(new ToolButton("preferences"));
        
        this.add(new ToolButton("quit"));
    }

    void gameLaunched(Boolean network)
    {
        this.newGame.setEnabled(false);
        this.hostGame.setEnabled(false);
        this.joinGame.setEnabled(false);
        if ( ! network )
            this.saveGame.setEnabled(true);
        this.loadGame.setEnabled(false);
        this.bestMove.setEnabled(true);
        if ( ! network )
            this.undo.setEnabled(true);
    }
}
