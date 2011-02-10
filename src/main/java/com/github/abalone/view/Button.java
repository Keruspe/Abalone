package com.github.abalone.view;

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
public class Button extends JButton implements ActionListener
{
    private final String type;
    private final SVGIcon icon;
    private final Window window;

    public Button(Window window, String type)
    {
        super();

        this.window = window;
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
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if ( this.type.equals("quit") )
        {
            this.window.quit();
        }
    }
}
