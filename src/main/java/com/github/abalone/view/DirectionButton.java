package com.github.abalone.view;

import com.github.abalone.config.Config;
import com.github.abalone.controller.GameController;
import com.github.abalone.util.Direction;
import com.kitfox.svg.app.beans.SVGIcon;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author sardemff7
 */
class DirectionButton extends JButton implements ActionListener
{
    private final Direction direction;
    private final SVGIcon icon;
    private final Board board;
    
    DirectionButton(Direction direction, Board board)
    {
        super();
        
        this.direction = direction;
        this.board = board;

        Dimension d = new Dimension(30, 30);

        this.icon = new SVGIcon();
        this.icon.setScaleToFit(true);
        this.icon.setAntiAlias(true);
        this.icon.setPreferredSize(d);
        try
        {
            this.icon.setSvgURI(getClass().getResource("game/" + Config.get("theme") + "/" + this.direction.name() + ".svg").toURI());
        }
        catch (URISyntaxException ex)
        {
            Logger.getLogger(ToolButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIcon(this.icon);

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.board.move(this.direction);
    }

}
