package grafika.paint.transformacje;

import grafika.exceptions.GraphicsException;
import grafika.paint.DrawingPanel;
import grafika.paint.figury.Point;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RotateWindow extends TransformationWindow {

    private JTextField centerPointInput;
    private JButton centerPointButton;

    private JTextField angleInput;

    public RotateWindow(DrawingPanel panel) {
        super("Rotation", panel);
    }

    private MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            getPanel().getTransformation().setStartPoint(Point.getPoint(e.getPoint()));
            centerPointInput.setText(Point.getPoint(e.getPoint()).toString());
            getPanel().addMouseListener(getPanel().getMyAdapter());
            getPanel().addMouseMotionListener(getPanel().getMyAdapter());
            getPanel().removeMouseListener(adapter);
            getPanel().removeMouseMotionListener(adapter);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            centerPointInput.setText(Point.getPoint(e.getPoint()).toString());
        }

    };

    @Override
    protected void customInit() {
        centerPointInput = new JTextField(8);
        centerPointButton = new JButton("Get Point");
        centerPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPanel().removeMouseListener(getPanel().getMyAdapter());
                getPanel().removeMouseMotionListener(getPanel().getMyAdapter());
                getPanel().addMouseListener(adapter);
                getPanel().addMouseMotionListener(adapter);
            }
        });

        getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getPanel().getTransformation().setAngle(Double.parseDouble(angleInput.getText()));
                } catch (Exception ex) {
                    getPanel().getTransformation().setAngle(23);
                }
                try {
                    getPanel().getTransformation().transform();
                } catch (GraphicsException ex) {
                    Logger.getLogger(TransformationWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        angleInput = new JTextField(8);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 0, 10, 0);
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel pointPanel = new JPanel();
        c.gridx = 0;
        c.gridy = 0;
        add(pointPanel, c);
        pointPanel.add(new JLabel("Center Point"));
        pointPanel.add(centerPointInput);
        pointPanel.add(centerPointButton);
        c.gridx = 0;
        c.gridy = 1;
        JPanel anglePanel = new JPanel();
        add(anglePanel, c);
        anglePanel.add(new JLabel("Angle"), c);
        anglePanel.add(angleInput, c);
    }

    @Override
    public void closeMe() {
        getPanel().setTransformation(null);
    }

}
