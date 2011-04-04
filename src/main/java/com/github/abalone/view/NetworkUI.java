package com.github.abalone.view;

import com.github.abalone.util.listeners.MessageListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author sardemff7
 */
public class NetworkUI extends JDialog implements ActionListener, MessageListener
{
    private final JTextArea messagesBox;
    private final JTextField entryBox;
    HashSet<MessageListener> listeners = new HashSet<MessageListener>();

    NetworkUI(Window window)
    {
        super(window, "Network");
        this.setSize(140, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        this.add(panel);
        
        this.messagesBox = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(this.messagesBox);
        this.messagesBox.setEditable(false);
        panel.add(scrollPane);

        this.entryBox = new JTextField();
        this.entryBox.addActionListener(this);
        panel.add(this.entryBox, BorderLayout.PAGE_END);
    }

    @Override
    public void newMessage(String message)
    {
        this.messagesBox.append("<Opponent> " + message + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String message = this.entryBox.getText();
        this.entryBox.setText("");
        this.messagesBox.append("<You> " + message + "\n");
        for ( MessageListener l : this.listeners )
            l.newMessage(message);
    }
    
    public void addMessageListener(MessageListener listener)
    {
        this.listeners.add(listener);
    }
}
