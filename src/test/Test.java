package test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test extends JPanel {

    private JPanel topPanel = new JPanel();
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();

    public Test() {
        super();
        addComponents();
        addTopPanel();
        addMiddlePanel();
        addBottomPanel();
    }

    private void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        add(topPanel, c);
        c.gridx = 0;
        c.gridy = 1;
        add(middlePanel, c);
        c.gridx = 0;
        c.gridy = 2;
        add(bottomPanel, c);
    }
    
    private void addTopPanel() {
        JTextField field = new JTextField();
    }
    
    private void addMiddlePanel() {
        
    }
    
    private void addBottomPanel() {
        
    }

}
