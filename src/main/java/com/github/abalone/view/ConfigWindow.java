package com.github.abalone.view;

import com.github.abalone.config.ConstraintValue;
import com.github.abalone.config.Value;
import java.awt.Component;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author sardemff7
 */
class ConfigWindow extends JFrame
{
    private final Box box;
    public ConfigWindow()
    {
        super("Preferences");

        this.setSize(300, 70);

        this.box = new Box(BoxLayout.PAGE_AXIS);

        Map<String, Value> config = com.github.abalone.config.Config.getConfig();
        for ( String key: config.keySet() )
        {
            Value value = config.get(key);
            Class cl = value.getType();
            if ( value instanceof ConstraintValue )
            {
                JComboBox list = new JComboBox();
                for ( Object option: ((ConstraintValue)value).getList() )
                {
                    list.addItem(option);
                }
                this.addConf(list, value.description);
            }
            else if ( cl.equals(Boolean.class) )
            {
                JCheckBox checkbox = new JCheckBox(value.description, (Boolean)value.get());
                this.addConf(checkbox, null);
            }
            else if ( cl.equals(String.class) )
            {
                JTextField field = new JTextField((String)value.get());
                this.addConf(field, value.description);
            }
        }

        this.add(this.box);
        
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void addConf(Component component, String description)
    {
        if ( description != null )
        {
            Box box = new Box(BoxLayout.LINE_AXIS);
            JLabel label = new JLabel(description);
            box.add(label);
            box.add(component);
            this.box.add(box);
        }
        else
            this.box.add(component);
    }
}
