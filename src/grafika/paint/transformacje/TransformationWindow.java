package grafika.paint.transformacje;

import grafika.exceptions.GraphicsException;
import grafika.paint.DrawingPanel;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public abstract class TransformationWindow extends JDialog {

    private JButton okButton;
    private JButton cancelButton;

    private DrawingPanel panel;

    protected GridBagConstraints c;

    public TransformationWindow(String title, DrawingPanel panel) {
        super();
        setTitle(title);
        this.panel = panel;
        init();
    }

    protected void init() {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        okButton = new JButton("OK");
        //////
        customInit();
        ///////

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                setOuterPreviewImage(null);
                TransformationWindow.this.dispatchEvent(new WindowEvent(TransformationWindow.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        JPanel buttonsPanel = new JPanel();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy++;
        add(buttonsPanel, c);
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                imageEditor.setPreviewImage(null);
//                getPanel().getTransformation().setHelperShape(null);
                closeMe();
                getPanel().repaint();
            }

        });
        setResizable(false);
        setAlwaysOnTop(true);
        pack();
        Container parent = panel.getParent().getParent().getParent();
        int width = getWidth();
        int height = getHeight();
        int parentX = parent.getLocationOnScreen().x;
        int parentY = parent.getLocationOnScreen().y;
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        int xPos = 0;
        int yPos = 0;

        yPos = parentY;

        if ((parentX + parentWidth) + width < screenWidth) {
            xPos = parentX + parentWidth;
        } else if (parentX - width > 0) {
            xPos = parentX - width;
        } else {
            xPos = (parentX + parentWidth) - width;
        }
        setLocation(xPos, yPos);

        setVisible(true);
    }

    public abstract void closeMe();

    public DrawingPanel getPanel() {
        return panel;
    }

    public void setPanel(DrawingPanel panel) {
        this.panel = panel;
    }

    protected abstract void customInit();

    public JButton getOkButton() {
        return okButton;
    }

}
