package com.github.abalone.view;

import com.github.abalone.controller.Game;
import com.kitfox.svg.app.beans.SVGIcon;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author sardemff7
 */
public class Button extends JButton implements ActionListener
{
    private final String type;
    private final SVGIcon icon;
    private JFrame frame = null;

    public Button(String type)
    {
        super();

        this.type = type;

        Dimension d = new Dimension(30, 30);

        this.icon = new SVGIcon();
        this.icon.setScaleToFit(true);
        this.icon.setAntiAlias(true);
        this.icon.setPreferredSize(d);
        try
        {
            this.icon.setSvgURI(getClass().getResource("icons/" + this.type + ".svg").toURI());
        }
        catch (URISyntaxException ex)
        {
            Logger.getLogger(Button.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIcon(this.icon);

        this.addActionListener(this);
        
        if(this.type.equals("preferences"))
        {
            this.frame = new ConfigWindow();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if ( this.type.equals("new-game") )
        {
            Game.getInstance().launch();
        }
        else if(this.type.equals("save-game"))
        {
            Game.getInstance().save();
        }
        else if(this.type.equals("load-game"))
        {
            Game.getInstance().load();
        }
        else if(this.type.equals("preferences"))
        {
            this.frame.setVisible(true);
        }
        else if(this.type.equals("quit"))
        {
            Game.getInstance().quit();
        }
    }
}
