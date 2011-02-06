package com.github.abalone.view;

import java.awt.FlowLayout;
import javax.swing.JFrame;

/**
 *
 * @author sardemff7
 */
public class Window extends JFrame {
    
    private final Tools tools;
    
    public Window()
    {
        super("Abalone");

        this.setSize(100, 100);
        
        this.setLayout(new FlowLayout());

        this.tools = new Tools();
        this.add(this.tools);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
