package grafika.zad1.colorPicker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ColorPickerPanel extends JPanel {

    private JTabbedPane tabsPanel = new JTabbedPane();
    private JPanel slidersPanel = new JPanel();

    private RgbChoosingPanel rgbPanel = new RgbChoosingPanel();
    private HsvChoosingPanel hsvPanel = new HsvChoosingPanel();
    private CmykChoosingPanel cmykPanel = new CmykChoosingPanel();

    public ColorPickerPanel() {
        super();
        addComponents();
    }

    private void addComponents() {
//        setPreferredSize(new Dimension(600,200));
        setLayout(new FlowLayout());
        add(tabsPanel);
        add(slidersPanel);

        tabsPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabsPanel.setPreferredSize(new Dimension(200, 200));
//        tabsPanel.setTabPlacement(JTabbedPane.BOTTOM);
        tabsPanel.addTab("RGB", rgbPanel);
        tabsPanel.addTab("HSV", hsvPanel);

        slidersPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.ipady = 20;
        c.gridx = 0;
        c.gridy = 0;
        slidersPanel.add(new RgbSliderPanel(rgbPanel), c);
        c.gridx = 0;
        c.gridy = 1;
        slidersPanel.add(new HsvSliderPanel(hsvPanel), c);
        c.gridx = 0;
        c.gridy = 2;
        slidersPanel.add(new CmykSliderPanel(cmykPanel), c);
    }

}
