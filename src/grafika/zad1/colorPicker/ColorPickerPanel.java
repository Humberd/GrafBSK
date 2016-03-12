package grafika.zad1.colorPicker;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;

public class ColorPickerPanel extends JPanel {

    private JTabbedPane tabsPanel = new JTabbedPane();
    private JPanel slidersPanel = new JPanel();

    private RgbChoosingPanel rgbPanel = new RgbChoosingPanel();
    private HsvChoosingPanel hsvPanel = new HsvChoosingPanel();
    private CmykChoosingPanel cmykPanel = new CmykChoosingPanel();

    private ColorConverter converter = new ColorConverter();

    public ColorPickerPanel() {
        super();
        converter.setRgbPanel(rgbPanel);
        converter.setHsvPanel(hsvPanel);
        converter.setCmykPanel(cmykPanel);
        rgbPanel.setConverter(converter);
//        hsvPanel.setConverter(converter);
        cmykPanel.setConverter(converter);
        
        addComponents();

//        bindPanels();
    }

    @Deprecated
    private void bindPanels() {
        BindingGroup group = new BindingGroup();
        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, rgbPanel, ELProperty.create("${cyan}"), cmykPanel, BeanProperty.create("cyan"));
        group.addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, rgbPanel, ELProperty.create("${magneta}"), cmykPanel, BeanProperty.create("magneta"));
        group.addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, rgbPanel, ELProperty.create("${yellow}"), cmykPanel, BeanProperty.create("yellow"));
        group.addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, rgbPanel, ELProperty.create("${black}"), cmykPanel, BeanProperty.create("black"));
        group.addBinding(binding);
        group.bind();
//        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, cmykPanel, BeanProperty.create("red"), rgbPanel, BeanProperty.create("red"));
//        binding.bind();
    }

    private void addComponents() {
//        setPreferredSize(new Dimension(600,200));
        setLayout(new FlowLayout());
        add(tabsPanel);
        add(slidersPanel);

        tabsPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabsPanel.setPreferredSize(new Dimension(320, 400));
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
