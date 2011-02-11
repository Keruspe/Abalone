package com.github.abalone.view;

import com.github.abalone.util.Direction;
import java.awt.GridLayout;
import java.util.EnumMap;
import javax.swing.JPanel;

/**
 *
 * @author sardemff7
 */
public class DirectionSelector extends JPanel
{
    private EnumMap<Direction, DirectionButton> buttons;

    public DirectionSelector()
    {
        this.setLayout(new GridLayout(3, 2));

        this.buttons = new EnumMap(Direction.class);
        
        for ( Direction d: Direction.values() )
        {
            DirectionButton b = new DirectionButton(d);
            this.buttons.put(d, b);
            this.add(b);
        }
    }
}
