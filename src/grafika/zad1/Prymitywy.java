package grafika.zad1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Prymitywy extends JPanel {

    private DrawingPanel drawingPanel;
    private JPanel editPanel;
    private JPanel buttonsPanel;
    private JPanel figurePanel;
    private JPanel modifyPanel;

    private JLabel startXInputLabel = new JLabel("Start X");
    private JLabel startYInputLabel = new JLabel("Start Y");
    private JLabel endXInputLabel = new JLabel("End X");
    private JLabel endYInputLabel = new JLabel("End Y");

    private JTextField startXInput = new JTextField();
    private JTextField startYInput = new JTextField();
    private JTextField endXInput = new JTextField();
    private JTextField endYInput = new JTextField();

    private JButton translateButton = new JButton("Translate");
    private JButton rotateButton = new JButton("Rotate");
    private JButton scaleButton = new JButton("Scale");

    public Prymitywy() {
        super();
        drawingPanel = new DrawingPanel(this);
        buttonsPanel = new JPanel();
        modifyPanel = new JPanel();
        figurePanel = new JPanel();
        editPanel = new JPanel();
        editPanel.setVisible(false);
        setLayout(new BorderLayout());
        setName("jestem prymitywem");
        addComponents();
        addInputListeners();
    }

    private void addComponents() {
        add(drawingPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        add(editPanel, BorderLayout.WEST);
        addEditPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(figurePanel, BorderLayout.WEST);
        buttonsPanel.add(modifyPanel, BorderLayout.EAST);

        TitledBorder title;
        title = BorderFactory.createTitledBorder("Shapes");
        title.setTitleJustification(TitledBorder.CENTER);
        figurePanel.setBorder(title);

        title = BorderFactory.createTitledBorder("Modifications");
        title.setTitleJustification(TitledBorder.CENTER);
        modifyPanel.setBorder(title);

        JButton lineButton = addButton("Line", figurePanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setCurrentDrawingTemplate(new Line());
            }
        });
        JButton rectangleButton = addButton("Rectangle", figurePanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setCurrentDrawingTemplate(new Rectangle());
            }
        });
        JButton circleButton = addButton("Circle", figurePanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setCurrentDrawingTemplate(new Circle());
            }
        });
        JButton undoButton = addButton("Undo", modifyPanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.undoLastDrawing();
            }
        });
        JButton redoButton = addButton("Redo", modifyPanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.redoPreviousDrawing();
            }
        });
        JButton editButton = addButton("Edit", modifyPanel, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setInEditMode(!drawingPanel.isInEditMode());
                setInEditMode(drawingPanel.isInEditMode());
            }
        });
        ////////////////////////////////////////////////
        drawingPanel.setBackground(Color.white);
    }

    private void addEditPanel() {
        editPanel.setVisible(false);
        editPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 0.1;
        c.weighty = 0.1;
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        editPanel.add(startXInputLabel, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        editPanel.add(startXInput, c);

        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 2;
        editPanel.add(startYInputLabel, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        editPanel.add(startYInput, c);

        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 4;
        editPanel.add(endXInputLabel, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        editPanel.add(endXInput, c);

        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 6;
        editPanel.add(endYInputLabel, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        editPanel.add(endYInput, c);

        c.weightx = 0.2;
        c.weighty = 0.2;
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        editPanel.add(translateButton, c);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 9;
        editPanel.add(rotateButton, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 10;
        editPanel.add(scaleButton, c);
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               drawingPanel.setTransformation(new Translate());
            }
        });
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//               drawingPanel.setTransformation(new Rotate());
            }
        });
        scaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//               drawingPanel.setTransformation(new Scale());
            }
        });

    }

    private JButton addButton(String name, JPanel panel, ActionListener listener) {
        JButton button = new JButton(name);
        button.addActionListener(listener);
        panel.add(button);
        return button;
    }

    private void setInEditMode(boolean editMode) {
        editPanel.setVisible(editMode);
        drawingPanel.setTransformation(null);
    }

    private void clearEditModes() {

    }

    public void updateEditValues(int startX, int startY, int endX, int endY) {
        startXInput.setText(startX + "");
        startYInput.setText(startY + "");
        endXInput.setText(endX + "");
        endYInput.setText(endY + "");
    }

    private synchronized void updateEditModel() {
        try {
            int startX = Integer.parseInt(startXInput.getText());
            int startY = Integer.parseInt(startYInput.getText());
            int endX = Integer.parseInt(endXInput.getText());
            int endY = Integer.parseInt(endYInput.getText());
            drawingPanel.updateModelValues(startX, startY, endX, endY);
        } catch (Exception e) {
            System.out.println("EXCEPTION");
        }
    }

    private class myKeyAdapter extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateEditModel();
                }

            }).start();
        }
    }

    private void addInputListeners() {
        startXInput.addKeyListener(new myKeyAdapter());
        startYInput.addKeyListener(new myKeyAdapter());
        endXInput.addKeyListener(new myKeyAdapter());
        endYInput.addKeyListener(new myKeyAdapter());
    }

}
