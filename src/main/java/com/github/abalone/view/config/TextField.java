package com.github.abalone.view.config;

import com.github.abalone.config.Value;
import javax.swing.JTextField;

/**
 *
 * @author sardemff7
 */
class TextField extends JTextField
{
    public TextField(Value value)
    {
            super((String)value.get());
    }
}
