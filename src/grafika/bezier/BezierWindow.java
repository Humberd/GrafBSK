package grafika.bezier;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class BezierWindow extends JPanel {

    private JPanel optionsPanel = new JPanel();
    private BezierDrawingPanel bezierDrawingPanel = new BezierDrawingPanel(this);

    private JButton clearButton = new JButton();
    private JButton deleteLastPointButton = new JButton();

    private JCheckBox showLinesCheckBox = new JCheckBox("Show Lines");

    public BezierWindow() {
        super();
        setName("Krzywe Beziera");
        setLayout(new BorderLayout());
        addComponents();
        addListeners();
    }
    
    private void addListeners() {
        showLinesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bezierDrawingPanel.repaint();
            }
        });
        Action clearButtonAction = new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent e) {
                bezierDrawingPanel.clearPoints();
            }
        };
        clearButtonAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        clearButton.setAction(clearButtonAction);
        clearButton.getActionMap().put("Clear", clearButtonAction);
        clearButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke) clearButtonAction.getValue(Action.ACCELERATOR_KEY), "Clear");
        
        Action deleteButtonAction = new AbstractAction("Delete Last Point") {
            @Override
            public void actionPerformed(ActionEvent e) {
                bezierDrawingPanel.removeLastPoint();
            }
        };
        deleteButtonAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
        deleteLastPointButton.setAction(deleteButtonAction);
        deleteLastPointButton.getActionMap().put("Delete", deleteButtonAction);
        deleteLastPointButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke) deleteButtonAction.getValue(Action.ACCELERATOR_KEY), "Delete");
    }

    private void addComponents() {
        add(bezierDrawingPanel, BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.SOUTH);

        GridBagConstraints c = new GridBagConstraints();
        optionsPanel.setLayout(new GridBagLayout());
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(5, 10, 5, 10);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(clearButton);
        buttonsPanel.add(deleteLastPointButton);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(buttonsPanel, c);

        JPanel checkBoxesPanel = new JPanel();
        checkBoxesPanel.add(showLinesCheckBox);
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        optionsPanel.add(checkBoxesPanel, c);
    }

    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    public void setOptionsPanel(JPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }

    public BezierDrawingPanel getBezierDrawingPanel() {
        return bezierDrawingPanel;
    }

    public void setBezierDrawingPanel(BezierDrawingPanel bezierDrawingPanel) {
        this.bezierDrawingPanel = bezierDrawingPanel;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public void setClearButton(JButton clearButton) {
        this.clearButton = clearButton;
    }

    public JButton getDeleteLastPointButton() {
        return deleteLastPointButton;
    }

    public void setDeleteLastPointButton(JButton deleteLastPointButton) {
        this.deleteLastPointButton = deleteLastPointButton;
    }

    public JCheckBox getShowLinesCheckBox() {
        return showLinesCheckBox;
    }

    public void setShowLinesCheckBox(JCheckBox showLinesCheckBox) {
        this.showLinesCheckBox = showLinesCheckBox;
    }
}
