package com.github.abalone.view.config;

import com.github.abalone.config.Value;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 *
 * @author sardemff7
 */
class CheckBox extends JCheckBox implements ActionListener
{
    CheckBox(Value value)
    {
        super(value.description, (Boolean)value.get());
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }
}
