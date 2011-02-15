package com.github.abalone.view.config;

import com.github.abalone.config.ConstraintValue;
import com.github.abalone.config.Value;
import javax.swing.JComboBox;

/**
 *
 * @author sardemff7
 */
class ComboBox extends JComboBox
{
    ComboBox(Value value)
    {
        for ( Object option: ((ConstraintValue)value).getList() )
        {
            this.addItem(option);
        }
    }
}
