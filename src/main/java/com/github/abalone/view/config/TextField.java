package com.github.abalone.view.config;

import com.github.abalone.config.Value;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 *
 * @author sardemff7
 */
class TextField extends JTextField implements ActionListener
{
    private final Value value;
    public TextField(Value value)
    {
            super((String)value.get());
            this.value = value;
            this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        this.value.set(this.getText());
    }
}
