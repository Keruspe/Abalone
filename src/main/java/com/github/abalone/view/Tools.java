/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.abalone.view;

import javax.swing.JButton;
import javax.swing.JToolBar;
import com.kitfox.svg.SVGUniverse;

/**
 *
 * @author sardemff7
 */
class Tools extends JToolBar
{

    public Tools()
    {
        JButton button;

        button = new JButton("New game");
        this.add(button);

        button = new JButton("Load game");
        this.add(button);
    }

}
