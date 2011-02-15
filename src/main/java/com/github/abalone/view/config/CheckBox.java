package com.github.abalone.view.config;

import com.github.abalone.config.Value;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;

/**
 *
 * @author sardemff7
 */
class CheckBox extends JCheckBox implements ItemListener
{
    private final Value value;

    CheckBox(Value value)
    {
        super(value.description, (Boolean)value.get());
        this.value = value;
        this.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        if ( ie.getStateChange() == ItemEvent.SELECTED )
            this.value.set(Boolean.TRUE);
        else
            this.value.set(Boolean.FALSE);
    }
}
