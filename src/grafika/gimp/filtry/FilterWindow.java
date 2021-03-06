package grafika.gimp.filtry;

import grafika.gimp.ImageEditor;
import grafika.gimp.ImageWindow;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

public abstract class FilterWindow extends JDialog {

    private ImageEditor imageEditor;
    private ImageWindow imageWindow;

    private JButton okButton;
    private JButton cancelButton;

    private JCheckBox previewImageCheckBox;

    private boolean showButtons = true;
    private boolean showPreviewImageCheckBox = true;

    protected GridBagConstraints c;

    private BufferedImage innerPreviewImage;

    private boolean centerPosition;

    public FilterWindow(String title, ImageWindow imageWindow, boolean centerPosition) {
        super();
        this.imageEditor = imageWindow.getImageEditor();
        this.imageWindow = imageWindow;
        this.centerPosition = centerPosition;
        setTitle(title);
        init();
    }

    public FilterWindow(String title, ImageWindow imageWindow) {
        this(title, imageWindow, true);
    }

    protected void init() {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        ////
        customInit();
        ////

        c.insets = new Insets(0, 0, 0, 0);
        if (showPreviewImageCheckBox) {
            previewImageCheckBox = new JCheckBox("Preview Image");
            previewImageCheckBox.setSelected(true);
            previewImageCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AbstractButton absButton = (AbstractButton) e.getSource();
                    ButtonModel modButton = absButton.getModel();
                    if (modButton.isSelected() == true) {
                        setOuterPreviewImage(getInnerPreviewImage());
                    } else {
                        setOuterPreviewImage(null);
                    }
                }
            });
            JPanel checkBoxPanel = new JPanel();
            c.anchor = GridBagConstraints.LINE_START;
            c.gridx = 0;
            c.gridy++;
            add(checkBoxPanel, c);
            checkBoxPanel.add(previewImageCheckBox);
        }

        if (showButtons) {
            okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getInnerPreviewImage() != null) {
                        pushNewImage(getInnerPreviewImage());
                    }
                    FilterWindow.this.dispatchEvent(new WindowEvent(FilterWindow.this, WindowEvent.WINDOW_CLOSING));
                }
            });
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setOuterPreviewImage(null);
                    FilterWindow.this.dispatchEvent(new WindowEvent(FilterWindow.this, WindowEvent.WINDOW_CLOSING));
                }
            });

            JPanel buttonsPanel = new JPanel();
            c.anchor = GridBagConstraints.CENTER;
            c.gridx = 0;
            c.gridy++;
            add(buttonsPanel, c);
            buttonsPanel.add(okButton);
            buttonsPanel.add(cancelButton);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                imageEditor.setPreviewImage(null);
                closeMe();
            }

        });
        setResizable(false);
        setAlwaysOnTop(true);
        pack();
        Container parent = imageWindow.getParent().getParent().getParent();
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

        if (centerPosition == true) {
            yPos = (parentY + parentHeight) - height;
        } else {
            yPos = parentY;
        }
        
        if ((parentX + parentWidth) + width < screenWidth) {
            xPos = parentX + parentWidth;
        } else if (parentX - width > 0) {
            xPos = parentX - width;
        } else {
            xPos = (parentX + parentWidth) - width;
        }
        setLocation(xPos, yPos);

        setVisible(true);

        invokeAfterBuild();
    }

    protected abstract void customInit();

    protected void invokeAfterBuild() {

    }

    public BufferedImage getImage() {
        return imageEditor.getImage();
    }

    public void pushNewImage(BufferedImage image) {
        imageEditor.pushNewImage(image);
    }

    public BufferedImage getInnerPreviewImage() {
        return innerPreviewImage;
    }

    public void setInnerPreviewImage(BufferedImage innerPreviewImage) {
        this.innerPreviewImage = innerPreviewImage;
    }

    public BufferedImage getOuterPreviewImage() {
        return imageEditor.getPreviewImage();
    }

    public void setOuterPreviewImage(BufferedImage outerPreviewImage) {
        imageEditor.setPreviewImage(outerPreviewImage);
    }

    public ImageWindow getWindow() {
        return imageWindow;
    }

    public abstract void closeMe();

    public JButton getOkButton() {
        return okButton;
    }

    public void setOkButton(JButton okButton) {
        this.okButton = okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JCheckBox getPreviewImageCheckBox() {
        return previewImageCheckBox;
    }

    public void setPreviewImageCheckBox(JCheckBox previewImageCheckBox) {
        this.previewImageCheckBox = previewImageCheckBox;
    }

    public boolean isShowButtons() {
        return showButtons;
    }

    public void setShowButtons(boolean showButtons) {
        this.showButtons = showButtons;
    }

    public boolean isShowPreviewImageCheckBox() {
        return showPreviewImageCheckBox;
    }

    public void setShowPreviewImageCheckBox(boolean showPreviewImageCheckBox) {
        this.showPreviewImageCheckBox = showPreviewImageCheckBox;
    }

}
