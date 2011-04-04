package com.github.abalone.view.config;

import com.github.abalone.config.Value;
import com.github.abalone.config.Range;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author sardemff7
 */
class Slider extends JSlider implements ChangeListener
{

    public Slider(Value value)
    {
        super(((Range)value).getMin(), ((Range)value).getMax(), (Integer)value.get());
        this.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent ce)
    {
        
    }

}
