package com.github.abalone.view.config;

import com.github.abalone.config.ConstraintValue;
import com.github.abalone.config.Range;
import com.github.abalone.config.Value;
import java.awt.Component;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author sardemff7
 */
public class Window extends JFrame
{
    private final Box box;
    public Window()
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
                this.addConf(new ComboBox(value), value.description);
            }
            else if ( value instanceof Range )
            {
                this.addConf(new Slider(value), value.description);
            }
            else if ( cl.equals(Boolean.class) )
            {
                this.addConf(new CheckBox(value), null);
            }
            else if ( cl.equals(String.class) )
            {
                this.addConf(new TextField(value), value.description);
            }
            else
                throw new RuntimeException(String.format("Value type not supported yet: %s", cl));
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
