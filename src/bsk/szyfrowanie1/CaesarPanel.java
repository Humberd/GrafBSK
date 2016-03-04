package bsk.szyfrowanie1;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CaesarPanel extends TemplatePanel {

    private JLabel keyInput2Label = new JLabel("Key k1:");
    private JTextField keyInput2 = new JTextField();
    
    public CaesarPanel(Cipher cipher) {
        super(cipher);
        addComponents();
    }

    private void addComponents() {
        super.getKeyInputLabel().setText("Key k0:");
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 0.01;
        c.weightx = 0.01;
        c.insets = new Insets(0, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 2;
//        super.getInputPs

    }

}
