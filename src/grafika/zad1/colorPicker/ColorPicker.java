package grafika.zad1.colorPicker;

import grafika.zad1.DrawingPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sun.swing.SwingUtilities2;

public class ColorPicker {

    private static DrawingPanel panel;
    
    public static void pickAColor() {
        ColorPickerPanel colorPanel = new ColorPickerPanel();
        JDialog dialog = new ColorChooserDialog(colorPanel);
        dialog.setVisible(true);
    }

    public static DrawingPanel getPanel() {
        return panel;
    }

    public static void setPanel(DrawingPanel aPanel) {
        panel = aPanel;
    }

    public static void setColor(Color color) {
        panel.setSelectedColor(color);
    }
}

class ColorChooserDialog extends JDialog {

    private ColorPickerPanel chooserPane;
    private JButton cancelButton;

    public ColorChooserDialog(ColorPickerPanel pane) {
        this.chooserPane = pane;
        initColorChooserDialog();
    }

    private void initColorChooserDialog() {
        Locale locale = getLocale();
        String okString = UIManager.getString("ColorChooser.okText", locale);
        String cancelString = UIManager.getString("ColorChooser.cancelText", locale);
        String resetString = UIManager.getString("ColorChooser.resetText", locale);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(chooserPane, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton okButton = new JButton(okString);
//        getRootPane().setDefaultButton(okButton);
        okButton.getAccessibleContext().setAccessibleDescription(okString);
        okButton.setActionCommand("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ColorChoosingPanel foo = (ColorChoosingPanel) chooserPane.getTabsPanel().getSelectedComponent();
                ColorPicker.setColor(foo.getColorPreview().getBackground());
            }
        });
        buttonPane.add(okButton);

        cancelButton = new JButton(cancelString);
        cancelButton.getAccessibleContext().setAccessibleDescription(cancelString);

        Action cancelKeyAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                ((AbstractButton) e.getSource()).doClick();//????????????
            }
        };

        KeyStroke cancelKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        InputMap inputMap = cancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = cancelButton.getActionMap();
        if (inputMap != null && actionMap != null) {
            inputMap.put(cancelKeyStroke, "cancel");
            actionMap.put("cancel", cancelKeyAction);
        }
        
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        buttonPane.add(cancelButton);
        
        JButton resetButton = new JButton(resetString);
        resetButton.getAccessibleContext().setAccessibleDescription(resetString);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                chooserPane.setColor(Color.WHITE);
                System.out.println("Jak klikasz reset to ustaw reset kolorow!!!");
            }
        });
        int mnemonic = SwingUtilities2.getUIDefaultsInt("ColorChooser.resetMnemonic", locale, -1);
        if (mnemonic != -1) {
            resetButton.setMnemonic(mnemonic);
        }
//        buttonPane.add(resetButton);
        contentPane.add(buttonPane, BorderLayout.SOUTH);
        
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//            MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
//            UIManager.setLookAndFeel(new MetalLookAndFeel());
//        if (JDialog.isDefaultLookAndFeelDecorated()) {
//            boolean supportsWindowDecorations = UIManager.getLookAndFeel().getSupportsWindowDecorations();
//            if (supportsWindowDecorations) {
//                getRootPane().setWindowDecorationStyle(JRootPane.COLOR_CHOOSER_DIALOG);
//            }
//        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ColorChooserDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ColorChooserDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ColorChooserDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ColorChooserDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        applyComponentOrientation(getRootPane().getComponentOrientation());
        
        pack();
        setLocationRelativeTo(null);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelButton.doClick(0);
                Window w = e.getWindow();
                w.setVisible(false);
            }
        });
    }
}
