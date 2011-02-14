package com.github.abalone.view;

import com.github.abalone.util.Direction;
import java.awt.GridLayout;
import java.util.EnumMap;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author sardemff7
 */
public class DirectionSelector extends JPanel
{
    private EnumMap<Direction, DirectionButton> buttons;

    DirectionSelector(Board board)
    {
        this.setLayout(new GridLayout(3, 2));

        this.buttons = new EnumMap(Direction.class);
        
        for ( Direction d: Direction.values() )
        {
            DirectionButton b = new DirectionButton(d, board);
            b.setVisible(false);
            this.buttons.put(d, b);
            this.add(b);
        }
    }

    void updateButtons(Set<Direction> valids)
    {
        for ( DirectionButton b: this.buttons.values() )
            b.setVisible(false);
        if ( valids == null )
            return;
        for ( Direction d: valids )
            this.buttons.get(d).setVisible(true);
    }
}
